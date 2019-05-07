package com.jayyin.spring.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * @author jerryyin
 * @version 1.0
 * @name CusApplicationEventPublisher
 * @description TODO
 *      自定义事件发布者
 *
 * @date 2019/5/7 10:15
 **/
public class CusApplicationEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


    public void publish(){
        CusApplicationEvent cusEvent = new CusApplicationEvent(this);
        publisher.publishEvent(cusEvent);
    }


}
