//package top.torx.core.component.lock;
//
//import cn.hutool.core.util.StrUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//
///**
// * Lock锁切面
// *
// * @Author: LiuYuHua
// * @Date: 2024/12/22 15:41
// */
//@RequiredArgsConstructor
//@Slf4j
//@Aspect
//public class LockAspect {
//
//    private final LockOps lockOps;
//
//    @Pointcut("@annotation(top.torx.core.component.lock.Lock)")
//    public void lockAspect() {
//
//    }
//
//    @Around("lockAspect()")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        Lock lockAnnotation = getTargetAnnotation(joinPoint);
//        if (lockAnnotation == null) {
//            return joinPoint.proceed();
//        }
//        String lockKey = getLockKey(joinPoint, lockAnnotation);
//        boolean lock = lockOps.lock(lockKey, lockAnnotation.timeout(), lockAnnotation.waitTimes());
//        if (!lock) {
//            log.debug("获取锁失败，key：{}", lockKey);
//            throw BizException.wrap(ExceptionCode.A0502.build(lockAnnotation.msg()));
//        }
//        try {
//            return joinPoint.proceed();
//        } finally {
//            lockOps.releaseLock(lockKey);
//        }
//    }
//
//    private String getLockKey(JoinPoint joinPoint, Lock lock) {
//        String lockKey = lock.value();
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        if (StrUtil.isEmpty(lockKey)) {
//            lockKey = StrUtil.join(StrUtil.COLON, "LOCK", joinPoint.getTarget().getClass().getName(), methodSignature.getName());
//        } else {
//            lockKey = SpelHelper.getValBySpEL(lockKey, methodSignature.getMethod(), joinPoint.getArgs());
//        }
//        return lockKey;
//    }
//
//    private Lock getTargetAnnotation(ProceedingJoinPoint joinPoint) {
//        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
//        return method.getAnnotation(Lock.class);
//    }
//}