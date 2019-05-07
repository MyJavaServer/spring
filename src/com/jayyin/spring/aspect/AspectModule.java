package com.jayyin.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author jerryyin
 * @version 1.0
 * @name AspectModule
 * @description TODO
 *  基于注解的切面apo
 *
 * @date 2019/5/7 13:42
 **/
@Aspect
public class AspectModule {


    /**
     * 定义一个切入点
     */
    //    @Pointcut("execution(* com.jayyin.spring.beans.*.*(..))")
    @Pointcut("execution(* com.jayyin.spring.beans.HelloSpringBean.getMessage(..))")
    private void getMessage() {}


    @Before("getMessage()")
    public void before(JoinPoint joinPoint){
        String url = joinPoint.getSignature().getDeclaringTypeName() + "." +
                joinPoint.getSignature().getName();
        System.out.println("[aop-before] url=" + url);
        System.out.println("[aop-before] " + joinPoint.toString());
    }

    @AfterThrowing(pointcut = "getMessage()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e){
        String url = joinPoint.getSignature().getDeclaringTypeName() + "." +
                joinPoint.getSignature().getName();
        System.out.println("[aop-throwing] url=" + url);
        System.out.println("[aop-throwing] " + e.toString());
        System.out.println("[aop-throwing] " + e.toString());

    }


}
