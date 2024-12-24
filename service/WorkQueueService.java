package service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RPatternTopic;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class WorkQueueService {

    @Autowired
    private RedissonClient redissonClient;
    
    private static final int CONSUMER_COUNT = 4;
    private static final int MAX_RETRY_COUNT = 3;
    private static final long ACK_TIMEOUT_SECONDS = 300; // 5分钟超时
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(CONSUMER_COUNT);

    /**
     * 发送消息
     */
    public void sendMessage(String key, String message) {
        String messageId = UUID.randomUUID().toString();
        MessageWrapper wrapper = new MessageWrapper(messageId, message);
        
        // 1. 记录消息状态
        RMap<String, MessageStatus> statusMap = getStatusMap(key);
        statusMap.put(messageId, new MessageStatus(MessageState.PENDING));
        
        // 2. 发送到队列
        RQueue<MessageWrapper> queue = getQueue(key);
        queue.add(wrapper);
        
        log.info("发送消息到队列：{}，messageId：{}", key, messageId);
    }

    /**
     * 启动消费者
     */
    private void startConsumersForType(String type) {
        for (int i = 0; i < CONSUMER_COUNT; i++) {
            final int consumerId = i;
            executorService.submit(() -> {
                String consumerName = type + "-consumer-" + consumerId;
                
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        RPatternTopic patternTopic = redissonClient.getPatternTopic(type + ":*");
                        Set<String> keys = patternTopic.getPatternNames();
                        
                        for (String key : keys) {
                            RQueue<MessageWrapper> queue = getQueue(key);
                            MessageWrapper wrapper = queue.poll();
                            
                            if (wrapper != null) {
                                processMessageWithAck(key, wrapper, consumerName);
                            }
                        }
                        
                        // 检查超时的消息
                        checkTimeoutMessages(type);
                        Thread.sleep(100);
                    } catch (Exception e) {
                        log.error("消费者{}处理消息异常", consumerName, e);
                    }
                }
            });
        }
    }

    /**
     * 处理消息（带��认机制）
     */
    private void processMessageWithAck(String key, MessageWrapper wrapper, String consumerName) {
        String messageId = wrapper.getMessageId();
        RMap<String, MessageStatus> statusMap = getStatusMap(key);
        
        try {
            // 1. 更新状态为处理中
            MessageStatus status = new MessageStatus(MessageState.PROCESSING);
            status.setConsumer(consumerName);
            status.setStartTime(System.currentTimeMillis());
            statusMap.put(messageId, status);
            
            // 2. 处理消息
            processMessage(key, wrapper.getMessage(), consumerName);
            
            // 3. 确认消息处理成功
            status.setState(MessageState.COMPLETED);
            status.setEndTime(System.currentTimeMillis());
            statusMap.put(messageId, status);
            
            log.info("消息处理成功 - key: {}, messageId: {}", key, messageId);
            
        } catch (Exception e) {
            log.error("消息处理失败 - key: {}, messageId: {}", key, messageId, e);
            
            MessageStatus status = statusMap.get(messageId);
            status.setRetryCount(status.getRetryCount() + 1);
            
            // 超过重试次数，标记为失败
            if (status.getRetryCount() >= MAX_RETRY_COUNT) {
                status.setState(MessageState.FAILED);
                statusMap.put(messageId, status);
                // 存入死信队列
                RQueue<MessageWrapper> dlq = redissonClient.getQueue("dlq:" + key);
                dlq.add(wrapper);
            } else {
                // 重新放回队列重试
                status.setState(MessageState.PENDING);
                statusMap.put(messageId, status);
                RQueue<MessageWrapper> queue = getQueue(key);
                queue.add(wrapper);
            }
        }
    }

    /**
     * 检查超时的消息
     */
    private void checkTimeoutMessages(String type) {
        RPatternTopic patternTopic = redissonClient.getPatternTopic(type + ":*");
        Set<String> keys = patternTopic.getPatternNames();
        
        for (String key : keys) {
            RMap<String, MessageStatus> statusMap = getStatusMap(key);
            
            for (Map.Entry<String, MessageStatus> entry : statusMap.entrySet()) {
                MessageStatus status = entry.getValue();
                
                if (status.getState() == MessageState.PROCESSING) {
                    long processingTime = System.currentTimeMillis() - status.getStartTime();
                    
                    if (processingTime > ACK_TIMEOUT_SECONDS * 1000) {
                        // 超时的消息重新放回队列
                        status.setState(MessageState.PENDING);
                        status.setRetryCount(status.getRetryCount() + 1);
                        statusMap.put(entry.getKey(), status);
                        
                        log.warn("消息处理超时 - key: {}, messageId: {}", key, entry.getKey());
                    }
                }
            }
        }
    }

    // 工具方法
    private RQueue<MessageWrapper> getQueue(String key) {
        return redissonClient.getQueue("queue:" + key);
    }
    
    private RMap<String, MessageStatus> getStatusMap(String key) {
        return redissonClient.getMap("status:" + key);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void adminOnlyMethod() {
        // 只有管理员可以访问的方法
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void userMethod() {
        // 用户和管理员都可以访问的方法
    }
} 