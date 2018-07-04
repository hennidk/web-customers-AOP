package com.luv2code.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/*
 * In order to apply what we have learned in the previous excersises regarding AOP and using Aspects and Pointcut expressions, 
 * this class will implement several logging functions using AOP on our existing customer-tracker web app.
 * 
 * In this class we will simply apply previous course work to create logging functionality on several classes.
 */

@Aspect
@Component
public class CRMLoggingAspect {
	
	// setup logger
	private Logger logger = Logger.getLogger(getClass().getName());
	
	//setup pointcut declarations
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDAOPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {}
	
	//add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint jp) {
		
		//display the method we are calling
		String method = jp.getSignature().toShortString();
		logger.info("===> in @Before: calling method: " + method);
		
		// display the arguments to the method
		
		//get the arguments
		Object[] args = jp.getArgs();
		//loop through and display the arguments
		for (Object arg : args) {
			logger.info("===> Argument: " + arg);
		}
	}
	
	//add @AfterReturning advice
	@AfterReturning(pointcut = "forAppFlow()", returning = "result")
	public void after(JoinPoint jp, Object result) {
		
		// display method we are returning from
		String method = jp.getSignature().toShortString();
		logger.info("===> in @AfterReturning: from method: " + method);
		
		//display data returned by method
		logger.info("==> Result: " + result);
	}
	
}
