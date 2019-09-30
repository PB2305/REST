package com.yash.springboot.rest.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogService {

	Logger LOGGER;

	@Pointcut(value = "within(com.yash.springboot.rest.controller.EmployeeController)")
	public void employeeControllerPointcut() {
	}

	@Pointcut(value = "within(com.yash.springboot.rest.service.EmployeeServiceImpl)")
	public void employeeServiceImplPointcut() {
	}

	@Before("employeeControllerPointcut() || employeeServiceImplPointcut()")
	public void logBefore(JoinPoint joinPoint) {
		LOGGER = Logger.getLogger(joinPoint.getSignature().getDeclaringTypeName());
		LOGGER.log(Level.INFO, "Before : " + joinPoint.getSignature().getName());
	}

	@AfterReturning("employeeControllerPointcut() || employeeServiceImplPointcut()")
	public void logReturning(JoinPoint joinPoint) {
		LOGGER = Logger.getLogger(joinPoint.getSignature().getDeclaringTypeName());
		LOGGER.log(Level.INFO, "Returning from : " + joinPoint.getSignature().getName());
	}

	@AfterThrowing("employeeControllerPointcut() || employeeServiceImplPointcut()")
	public void logThrowing(JoinPoint joinPoint) {
		LOGGER = Logger.getLogger(joinPoint.getSignature().getDeclaringTypeName());
		LOGGER.log(Level.INFO, "Throwing Exception from : " + joinPoint.getSignature().getName());
	}

	@After("employeeControllerPointcut() || employeeServiceImplPointcut()")
	public void logAfter(JoinPoint joinPoint) {
		LOGGER = Logger.getLogger(joinPoint.getSignature().getDeclaringTypeName());
		LOGGER.log(Level.INFO, "After : " + joinPoint.getSignature().getName());
	}
}
