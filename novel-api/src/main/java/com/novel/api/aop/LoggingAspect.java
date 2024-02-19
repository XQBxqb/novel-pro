package com.novel.api.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Before("execution(* com.novel..controller.*.*(..))")
    public void logControllerAccessBefore(JoinPoint joinPoint) {
        log.info(LocalDateTime.now()+" Controller Entering in Method :  " + joinPoint.getSignature().getName());
        log.info("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
    }

    @AfterReturning(pointcut = "execution(* com.novel..controller..*.*(..))", returning = "result")
    public void logControllerAccessAfter(JoinPoint joinPoint, Object result) {
        log.info(LocalDateTime.now()+" Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
        log.info("Method Return : " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.novel..controller..*.*(..))", throwing = "error")
    public void logControllerAccessAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.info("Method Exception : " + error);
    }

    @Before("execution(* com.novel..service.impl.*.*(..))")
    public void logServiceAccessBefore(JoinPoint joinPoint) {
        log.info( LocalDateTime.now()+" Service Entering in Method :  " + joinPoint.getSignature().getName());
        log.info("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
    }

    @AfterReturning(value = "execution(* com.novel..service.impl.*.*(..))",returning = "result")
    public void logServiceAccessBefore(JoinPoint joinPoint, Object result) {
        log.info(LocalDateTime.now()+" Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
        log.info("Method Return : " + result);
    }
}
