package com.luv2code.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	
	// Setup Logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	// Pointcut expressions
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	// add @Before advice
	@Before("forAppFlow()")
	public void beforeAdvice(JoinPoint joinPoint) {
		
		// Display methods called
		String method = joinPoint.getSignature().toShortString();
		myLogger.info("====>>>> in @Before: calling method: " + method);
		
		// Display method's arguments
		Object[] args = joinPoint.getArgs();
		
		for(Object tempArg : args) {
			myLogger.info("===>>> argument: " + tempArg);
		}
	}
	
	// add @AfterReturning advice
	@AfterReturning(pointcut="forAppFlow()", returning="theResult")
	public void afterReturningAdvice(JoinPoint theJoinPoint, Object theResult) {
		
		String method = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>>>> in @AfterReturning: calling method: " + method);
		
		// Dispalay returned data
		myLogger.info("===>>> Result: " + theResult);
	}

}
