package com.jayyin.spring;

/**
 * @author jerryyin
 * @version 1.0
 * @name HelloSpring
 * @description TODO
 * @date 2019/5/5 13:58
 **/
public class HelloSpring {

    private String message;

    public String getMessage() {
        System.out.println("Get Your Message: " + message);
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
