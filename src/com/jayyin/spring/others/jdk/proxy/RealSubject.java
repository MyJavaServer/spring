package com.jayyin.spring.others.jdk.proxy;

/**
 * @author jerryyin
 * @version 1.0
 * @name RealSubject
 * @description TODO
 * * 真实主题类
 *
 * @date 2019/6/4 23:40
 **/
public class RealSubject implements AbsSubject {


    @Override
    public void doSomething() {
        System.out.println("RealSubject do something...");
    }


}
