package com.jayyin.spring.aspect;

import org.aspectj.lang.JoinPoint;

/**
 * @author jerryyin
 * @version 1.0
 * @name JLogging
 * @description TODO
 *  自定义切面 aop
 *  实现一个类似 Logging 日志的功能类
 *
 * @date 2019/5/7 11:06
 **/
public class JLogging {

    public void beforeAdvice(JoinPoint joinPoint){
        String url = joinPoint.getSignature().getDeclaringTypeName() + "." +
                joinPoint.getSignature().getName();
        System.out.println("[aop-before] url=" + url);
        System.out.println("[aop-before] " + joinPoint.toString());
    }

    public void afterAdvice(JoinPoint joinPoint){
        System.out.println("[aop-after] " + joinPoint.toString());
    }


    public void returningAdvice(Object o){
        System.out.println("[aop-returning] " + o.toString());
    }


    public void throwingAdvice(Exception e){
        System.out.println("[aop-throwing] " + e.toString());
    }


    public void aroundAdvice(JoinPoint joinPoint){
        System.out.println("[aop-around] " + joinPoint.toString());
    }



}
