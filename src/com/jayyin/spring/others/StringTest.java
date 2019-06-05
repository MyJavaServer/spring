package com.jayyin.spring.others;

/**
 * @author jerryyin
 * @version 1.0
 * @name StringTest
 * @description TODO
 * @date 2019/6/6 00:28
 **/
public class StringTest {

    public static void main(String[] a){
//        测试字符串常量池
        String sa = "hello";    //第一次创建了 ""hello"字符串，就直接添加到了 常量池中，供后面其他地方使用；
        String sb = "hello";    //第二次创建，发现 常量池有该字符串， 则直接返回一个引用，并未再创建字符串；
        String sc = new String("hello");


        System.out.println(sa==sb);     //因此 ： true
//        System.out.println(sa.equals(sb));// true

        System.out.println(sa==sc);

        System.out.println(sa.hashCode());
        System.out.println(sb.hashCode());
        System.out.println(sc.hashCode());





    }
}
