package com.jayyin.spring.others.jdk.proxy;



/**
 * @author jerryyin
 * @version 1.0
 * @name Test
 * @description TODO
 * @date 2019/6/4 23:46
 **/
public class Test {

    public static void main(String[] a){
        //测试

        // 保存生成的代理类的字节码文件
        // 由于设置 sun.misc.ProxyGenerator.saveGeneratedFiles 的值为true,
        // 所以代理类的字节码内容保存在了项目根目录下，文件名为$Proxy0.class
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        AbsSubject subject = new JdkDynamicProxy(new RealSubject()).getProxy();

        subject.doSomething();


    }
}
