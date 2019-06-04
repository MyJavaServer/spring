package com.jayyin.spring.others.test;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * @author jerryyin
 * @version 1.0
 * @name ApplicationTest
 * @description TODO
 * @date 2019/6/4 20:54
 **/
public class ApplicationTest{

    public static void main(String[] a) {

        AtomicInteger integer = new AtomicInteger(10);

        System.out.println(integer.get());
        System.out.println(integer.getAndSet(11));
        System.out.println(integer.get());

        System.out.println("----");

        System.out.println(integer.get());
        System.out.println(integer.getAndAdd(1));
        System.out.println(integer.get());

        System.out.println("----");

        System.out.println(integer.get());
        System.out.println(integer.getAndDecrement());
        System.out.println(integer.get());

        System.out.println("----");

        System.out.println(integer.get());
        System.out.println(integer.getAndIncrement());
        System.out.println(integer.get());

        System.out.println("----");

        System.out.println(integer.get());
        System.out.println(integer.getAndUpdate(new IntUnaryOperator() {
            @Override
            public int applyAsInt(int operand) {
                return 10;
            }
        }));

        System.out.println(integer.get());



//        ThreadPoolExecutor
//        Thread thread = );

    }
}
