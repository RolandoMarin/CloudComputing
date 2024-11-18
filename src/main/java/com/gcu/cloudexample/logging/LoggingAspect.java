package com.gcu.cloudexample.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.gcu.cloudexample.dao.*.*(..))")
    public void daoMethods() {}

    // Log entry into method
    @Before("daoMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        logger.debug("[{}] Entering {}.{}() with arguments: {}",
                LocalDateTime.now(),
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    // Log exit from method
    @AfterReturning(pointcut = "daoMethods()", returning = "result")
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        logger.debug("[{}] Exiting {}.{}() with result: {}",
                LocalDateTime.now(),
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                result);
    }

    // Log exceptions thrown from method
    @AfterThrowing(pointcut = "daoMethods()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Throwable exception) {
        logger.error("[{}] Exception in {}.{}() with message: {}",
                LocalDateTime.now(),
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                exception.getMessage(), exception);
    }
}
