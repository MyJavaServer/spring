package com.jayyin.spring.beans;

import com.jayyin.spring.interfaces.InitializingBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

import javax.annotation.PostConstruct;

/**
 * @author jerryyin
 * @version 1.0
 * @name HelloSpringBean
 * @description TODO
 * @date 2019/5/5 13:58
 **/
public class HelloSpringBean implements InitializingBean, ApplicationListener<ContextStartedEvent> {

    private String message;

    public String getMessage() {
        System.out.println("Get Your Message: " + message);
        return message;
    }

    public HelloSpringBean(String message) {
        this.message = message;
        System.out.println("This md is constructor2 HelloSpringBean("+ message+")");
    }

    public void setMessage(String message) {
        this.message = message;
    }




    /** 测试bean 的生命周期 */

    public HelloSpringBean() {
        System.out.println("This md is constructor1 HelloSpringBean()");
    }

    @PostConstruct
    public void init2() {
        System.out.println("This md is PostConstruct()");
    }

    @Override
    public void init() {
        System.out.println("This md is beforeInit()");
    }

    @Override
    public void destroy() {
        System.out.println("This md is destroy()");
    }




    @Override
    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
        System.out.println("contextStartedEvent..." + contextStartedEvent);
    }


    //测试用主动跑出异常的方法
    public void printThrowException(){
        System.out.println("Exception reised");
        throw new IllegalArgumentException();
    }

}
