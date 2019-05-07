package com.jayyin.spring.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author jerryyin
 * @version 1.0
 * @name CusApplicationEvent
 * @description TODO
 *  自定义Spring事件
 *
 * @date 2019/5/7 10:12
 **/
public class CusApplicationEvent extends ApplicationEvent {

    public CusApplicationEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
//        return "My Custom CusApplicationEvent";
        return super.toString();
    }
}
