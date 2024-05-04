package com.wolroys.wellbeing.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class ErrorLoggingAspect {

    @AfterThrowing(pointcut = "@annotation(org.springframework.transaction.annotation.Transactional)", throwing = "error")
    public void doAfterThrowing(JoinPoint point, Throwable error) {
        log.error("Exception in method " + point.getSignature().getName() + "; Error: " + error);
    }
}
