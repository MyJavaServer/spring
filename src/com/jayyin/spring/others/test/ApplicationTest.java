package com.jayyin.spring.others.test;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;
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

    }


    public void threadPool(){
//                ThreadPoolExecutor

//        Thread thread = );

        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                2,
                10,
                TimeUnit.HOURS,
                queue,
                new ThreadPoolTaskExecutor());

        executor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


    //链表队列  无界队列，FIFO，可以无限向队列中添加任务，直到内存溢出
    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

    //数组队列  有界队列，FIFO，需要指定队列大小，如果队列满了，会触发线程池的RejectedExecutionHandler逻辑
    private ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(200);

    //  优先级队列，线程池会优先选取优先级高的任务执行，队列中的元素必须实现Comparable接口
    private PriorityBlockingQueue priorityBlockingQueue = new PriorityBlockingQueue();

    //一种阻塞队列，其中每个 put 必须等待一个 take，反之亦然。同步队列没有任何内部容量，甚至连一个队列的容量都没有。
    // 可以简单理解为是一个容量只有1的队列。Executors.newCachedThreadPool使用的是这个队列
    private SynchronousQueue synchronousQueue = new SynchronousQueue();





    class PriTask implements Comparable, Runnable {

        @Override
        public int compareTo(Object o) {
            return 0;
        }

        @Override
        public void run() {

        }
    }
}
