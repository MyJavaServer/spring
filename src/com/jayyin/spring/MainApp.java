package com.jayyin.spring;

import com.jayyin.spring.beans.HelloSpringBean;
import com.jayyin.spring.event.CusApplicationEventPublisher;
import com.jayyin.spring.jdbctest.impl.StudentJDBCTemplate;
import com.jayyin.spring.jdbctest.model.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
//        helloSpringBean.printThrowException();


        testJdbc(context);

    }

    private static void testJdbc(ApplicationContext context) {
        StudentJDBCTemplate studentJDBCTemplate =
                (StudentJDBCTemplate) context.getBean("studentJDBCTemplate");

        System.out.println("------Records Creation--------");
        studentJDBCTemplate.create("Zara", 11);
        studentJDBCTemplate.create("Nuha", 2);
        studentJDBCTemplate.create("Ayan", 15);

        System.out.println("------Listing Multiple Records--------");
        List<Student> students = studentJDBCTemplate.listStudents();
        for (Student record : students) {
            System.out.print("ID : " + record.getId());
            System.out.print(", Name : " + record.getName());
            System.out.println(", Age : " + record.getAge());
        }

        System.out.println("----Updating Record with ID = 2 -----");
        studentJDBCTemplate.update(2, 20);

        System.out.println("----Listing Record with ID = 2 -----");
        Student student = studentJDBCTemplate.getStudent(2);
        System.out.print("ID : " + student.getId());
        System.out.print(", Name : " + student.getName());
        System.out.println(", Age : " + student.getAge());
    }

    public void thread() {

        int PRICE_L = 10;
        int PRICE_S = 5;

        int carType = 0 ;
        AtomicInteger mCurNum = new AtomicInteger(500);
        final BigDecimal[] mMoneySum = {BigDecimal.ZERO};

        ScheduledExecutorService service = Executors.newScheduledThreadPool(500);

        Task task = new Task() {
            @Override
            public void run() {
                //超过最大事件不计费了
                if (this.times <= 12) {
                    this.times++;
                    if (carType == 0){
                        mMoneySum[0] = mMoneySum[0].add(new BigDecimal(PRICE_S));
                    }else if (carType == 1){
                        mMoneySum[0] = mMoneySum[0].add(new BigDecimal(PRICE_L));
                    }
                }else {
                    service.shutdown();
                }
            }
        };

        service.scheduleAtFixedRate(task, 0, 12, TimeUnit.HOURS);
        service.execute(task);


//        ThreadFactory factory = new ThreadF

//        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(500, new ThreadFactotyBuilder().);

    }

    public abstract class Task implements Runnable {
        public int times = 0;  //执行次数
    }

}
