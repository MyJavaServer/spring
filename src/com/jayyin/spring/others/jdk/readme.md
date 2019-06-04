jdk 相关知识点
    
    
    1.jdk动态代理
    
    https://www.cnblogs.com/dreamroute/p/5273888.html
    
    java中 动态代理 主要有 JDK 和 CGLIB 两种方式。
    区别主要是jdk是代理接口，而cglib是代理类。

    jdk的动态代理调用了Proxy.newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h) 方法。    
    通过该方法生成字节码，动态的创建了一个代理类，interfaces参数是该动态类所继承的所有接口，而继承InvocationHandler 接口的类则是实现在调用代理接口方法前后的具体逻辑