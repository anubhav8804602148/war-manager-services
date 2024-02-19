package com.war.manager.common.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class AspectLogging {
	
	@Before("execution(* com.war..*(..))")
    public void logBeforeMethodInvocation(JoinPoint joinPoint) {
        log.info("Log Before " + joinPoint.getSignature().toShortString() + " execution");
    }

    @AfterThrowing(pointcut = "execution(* com.war..*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Log Exception in " + joinPoint.getSignature().toShortString() + ": " + exception.getMessage());
    }
	

    @AfterReturning(pointcut = "execution(* com.war..*(..))")
    public void logAfterSuccess(JoinPoint joinPoint) {
        log.info("Log Success: " + joinPoint.getSignature().toShortString());
    }
}
