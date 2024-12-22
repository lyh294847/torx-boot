package top.torx.A;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: LiuYuHua
 * @Date: 2024/12/22 20:27
 */
public class A {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println("1");
                ThreadUtil.sleep(1000);
            });
        }
        System.out.println("2");
    }
}
