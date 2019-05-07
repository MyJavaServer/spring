package com.jayyin.spring;

import com.jayyin.spring.beans.HelloSpringBean;
import com.jayyin.spring.event.CusApplicationEventPublisher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jerryyin
 * @version 1.0
 * @name MainApp
 * @description TODO
 * @date 2019/5/5 13:57
 **/
public class MainApp {

    public static void main(String[] argv) {

        //第一步：使用框架 API ClassPathXmlApplicationContext() 来创建应用程序的上下文
        //这个 API 加载 beans 的配置文件并最终基于所提供的 API，它处理创建并初始化所有的对象，即在配置文件中提到的 beans。

        /**
         * 容器1 ApplicationContext (Spring上下文)  推荐 较高级的容器
         *
         * 该容器继承实现的基本常用接口有三个：
         *
         * FileSystemXmlApplicationContext  该容器从 XML 文件中加载已被定义的 bean。在这里，你需要提供给构造器 XML 文件的完整路径。
         * ClassPathXmlApplicationContext   该容器从 XML 文件中加载已被定义的 bean。在这里，你不需要提供 XML 文件的完整路径，只需正确配置 CLASSPATH 环境变量即可，因为，容器会从 CLASSPATH 中搜索 bean 配置文件。
         * WebXmlApplicationContext     该容器会在一个 web 应用程序的范围内加载在 XML 文件中已被定义的 bean。
         *
         */
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        // 或者
//        ApplicationContext context = new FileSystemXmlApplicationContext("//Users/jerryyin/workspace/intellijIDEA/spring/src/Beans.xml");

        /**
         * 容器2 BeanFactory   不推荐
         */
//        XmlBeanFactory context = new XmlBeanFactory(new ClassPathResource("Beans.xml"));


        //第二步：使用已创建的上下文的 getBean() 方法来获得所需的 bean。这个方法使用 bean 的 ID 返回一个最终可以转换为实际对象的通用对象。一旦有了对象，你就可以使用这个对象调用任何类的方法。
//        System.out.println("I'm gonna to create HelloSpringBean object...");
        HelloSpringBean helloSpringBean = (HelloSpringBean) context.getBean("helloSpringBean");
//        System.out.println("I was created HelloSpringBean object...");
        helloSpringBean.getMessage();

//        CarFactory carFactory = (CarFactory) context.getBean("carFactory");
//        carFactory.toString();


        // 测试容器关闭，销毁bean对象，以便达到关闭的生命周期
        // 你需要注册一个在 AbstractApplicationContext 类中声明的关闭 hook 的 registerShutdownHook() 方法。它将确保正常关闭，并且调用相关的 destroy 方法。
//        context.registerShutdownHook();

//        context.start();
//        context.publishEvent(new ContextStartedEvent(context));


        //测试自定义事件分发和接收
//        CusApplicationEventPublisher publisher = (CusApplicationEventPublisher) context.getBean("cusApplicationEventPublisher");
//        publisher.publish();
//        publisher.publish();


        //测试aop自定义切面
        helloSpringBean.printThrowException();



    }
}
