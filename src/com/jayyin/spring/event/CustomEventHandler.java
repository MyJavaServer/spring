package com.jayyin.spring.event;

import org.springframework.context.ApplicationListener;

/**
 * @author jerryyin
 * @version 1.0
 * @name CustomEventHandler
 * @description TODO
 *      自定义事件接受接收处理工具类
 *
 * @date 2019/5/7 10:25
 **/
public class CustomEventHandler implements ApplicationListener<CusApplicationEvent> {


    @Override
    public void onApplicationEvent(CusApplicationEvent cusApplicationEvent) {
        System.out.println("I've got the application event : "  + cusApplicationEvent);
        cusApplicationEvent.toString();
    }
}
