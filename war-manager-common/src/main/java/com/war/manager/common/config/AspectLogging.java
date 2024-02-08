package com.war.manager.common.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AspectLogging {
	
	@Before("@annotation(WarManagerLoggable)")
    public void logBeforeMethodInvocation(JoinPoint joinPoint) {
        log.info("Before " + joinPoint.getSignature().toShortString() + " execution");
    }

    @AfterThrowing(pointcut = "@annotation(WarManagerLoggable)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception in " + joinPoint.getSignature().toShortString() + ": " + exception.getMessage());
    }
	

    @AfterReturning(pointcut = "@annotation(WarManagerLoggable)")
    public void logAfterSuccess(JoinPoint joinPoint) {
        log.info("Success: " + joinPoint.getSignature().toShortString());
    }
}
