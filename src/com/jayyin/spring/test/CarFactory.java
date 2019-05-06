package com.jayyin.spring.test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jerryyin
 * @version 1.0
 * @name CarFactory
 * @description TODO
 * @date 2019/5/5 16:39
 **/
public class CarFactory {

    @Autowired
    private Car mCar;

    @Override
    public String toString() {
        return mCar.getName();
    }
}
