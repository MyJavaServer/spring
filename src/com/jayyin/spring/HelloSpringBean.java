package com.jayyin.spring;

import com.jayyin.spring.interfaces.InitializingBean;

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
    public void afterPropertiesSet() {
        System.out.println("This md is beforeInit()");
    }

    @Override
    public void afterDestroy() {
        System.out.println("This md is afterDestroy()");
    }
}
