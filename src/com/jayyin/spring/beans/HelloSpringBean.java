package com.jayyin.spring.beans;

import com.jayyin.spring.interfaces.InitializingBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author jerryyin
 * @version 1.0
 * @name HelloSpringBean
 * @description TODO
 * @date 2019/5/5 13:58
 **/
public class HelloSpringBean implements InitializingBean {

    private String message;

    public String getMessage() {
        System.out.println("Get Your Message: " + message);
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }




    /** 测试bean 的生命周期 */

    public HelloSpringBean() {
        System.out.println("This md is constructor HelloSpringBean()");
    }

    @Override
    public void init() {
        System.out.println("This md is beforeInit()");
    }

    @Override
    public void destroy() {
        System.out.println("This md is destroy()");
    }



}
