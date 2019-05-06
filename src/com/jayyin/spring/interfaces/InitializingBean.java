package com.jayyin.spring.interfaces;

/**
 * @author jerryyin
 * @version 1.0
 * @name InitializingBean
 * @description TODO
 *  测试 bean 对象的生命周期
 *
 * @date 2019/5/6 10:19
 **/
public interface InitializingBean {

    /**
     * 在配置初始化之后执行 （bean对象创建（实例化）之后）
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;


    /**
     * 在bean对象销毁之后
     *
     * @throws Exception
     */
    void afterDestroy() throws Exception;
}
