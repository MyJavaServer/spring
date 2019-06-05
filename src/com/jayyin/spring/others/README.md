spring 之外的一些其他 知识点用例


spring没问，然后hashmap,并发的map,线程池，原子类，JVM，分布式id，分布式事务，缓存一致性，如何提高吞吐量，dubbo原理，自己设计rpc

学习资源：
	https://github.com/doocs/advanced-java （推荐）
	https://github.com/Snailclimb/JavaGuide 


面试题目：

一、jdk动态代理 原理及实现？  （例如aspect切面）
	
	关键：实现 Invocationhandler接口, 重写 invoke()方法；
	

二、 ConcurrentHashMap 结构。 为什么线程安全、 并发量跟什么有关（有几个Segment桶 == 锁的数量， 就有多少的并发）
    
                   线程完全       jdk1.7                             jdk1.8
        HashMap     不安全       数组+链表                        链表长度>8链表自动转化为红黑树（提高查询效率）     
        ConHMap     安全      分段数组+链表，分段锁（segment）      链表长度>8链表自动转化为红黑树，并且摒弃了segment（桶）分段锁，
                                                               对数组节点node加锁，锁数量增多，效率提升n倍数
        结论：
            为什么线程安全？ 因为加锁了；
            并发量 取决于 锁的数量（1.7: 桶的数量；  1.8: 锁的数量，数组节点的数量）    


三、 Atomic原子类 实现原理
	
	CAS 无锁，死循环
    无锁操作是使用CAS(compare and swap)又叫做比较交换来鉴别线程是否出现冲突，出现冲突就重试当前操作直到没有冲突为止;
    CAS是一种乐观锁策，即不会阻塞其他线程，
    
    问题：
        1.ABA问题
            因为CAS会检查旧值有没有变化，这里存在这样一个有意思的问题。比如
        一个旧值A变为了成B，然后再变成A，刚好在做CAS时检查发现旧值并没有变化依然为A，但是实际上的确发生了变化。
        解决方案可以沿袭数据库中常用的乐观锁方式，添加一个版本号可以解决。原来的变化路径A->B->A就变成了1A->2B->3C。

        2.自旋时间过长
            使用CAS时非阻塞同步，也就是说不会将线程挂起，会自旋（无非就是一个死循环）进行下一次尝试，如果这里自旋时间过长对性能是很大的消耗。
            如果JVM能支持处理器提供的pause指令，那么在效率上会有一定的提升。
            
            这也是为什么atomic类不能在大并发的场景下适用的原因；  大并发导致自旋操作飙升，等于是很多的死循环 ——> CPU飙升，
            并发越高，失败的次数会越多，CAS如果长时间不成功，会极大的增加CPU的开销。因此CAS不适合竞争十分频繁的场景。

           使用场景
            小并发的 场景下使用；  
               为啥大并发不能用？（因为无锁，死循环+大并发 -》cpu 飙升）
           
   

四、线程池。***** 必问
	原理解析：
	
	1.关键参数： pool数量。队列长度	
			ThreadPoolExecutor(int corePoolSize,		核心线程数   the number of threads to keep in the pool, even if they are idle, unless {@code allowCoreThreadTimeOut} is set       
                              int maximumPoolSize,		最大数     the maximum number of threads to allow in the pool
                              long keepAliveTime,		空闲到销毁的时间    when the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
                              TimeUnit unit,			时间单位
                              BlockingQueue<Runnable> workQueue,	队列  the queue to use for holding tasks before they are
                                                                 *        executed.  This queue will hold only the {@code Runnable}
                                                                 *        tasks submitted by the {@code execute} method.        
                                                                 *  装Runnable（只包含被execute()方法提交的task任务）的任务队列
                              ThreadFactory threadFactory,		 创建新的线程时           the factory to use when the executor creates a new thread    那队列中的task任务创建新的线程
                              RejectedExecutionHandler handler)  线程池的拒绝策略（有）	the handler to use when execution is blocked
                                                                               *        because the thread bounds and queue capacities are reached
                                                                               *    即线程个数超标 和 队列塞满 的情况下，都会导致新任务添加失败，这时候如何执行策略？
                                                                               在它的 rejectedExecution(Runnable r, ThreadPoolExecutor executor) 方法中去处理

    详细的线程池参数介绍和使用：
    https://blog.csdn.net/jgteng/article/details/54409887

    ThreadFactory 
        内部包含一个 newThread(Runnable) 方法， 就是用来创建线程的;
    	定制线程thread

	2.线程池的拒绝策略：
    https://blog.csdn.net/jgteng/article/details/54411423
    
    其实都在ThreadPoolExecutor类中，定义了这四种已有的策略，查看源码即可理解；
        1.AbortPolicy(默认)       抛出异常
        2.DiscardPolicy           空方法，啥都不做，不管
        3.DiscardOldestPolicy     获取队列，删除队列头（最早的）任务[queue.poll()]，然后添加当前任务
        4.CallerRunsPolicy       急性子，直接执行，[r.cun()] 直接主线程中执行当前任务，
    以上几种还不满足，自定义:
        5.自定义策略   实现 RejectedExecutionHandler 接口，重写 rejectedExecution()方法，在里面添加自己的逻辑即可；

    3.BlockingQueue队列的类型和区别 （都是实现了 BlockingQueue 接口的）
        1.LinkedBlockingQueue   链表结构   无界队列，FIFO，可以无限向队列中添加任务，直到内存溢出    Executors.newFixedThreadPool
        2.ArrayBlockingQueue    数组结构   有界队列（初始化时设置容量）有界队列，FIFO，需要指定队列大小，如果队列满了，会触发线程池的RejectedExecutionHandler逻辑
        3.SynchronousQueue  一种阻塞队列，其中每个 put 必须等待一个 take，反之亦然。同步队列没有任何内部容量，甚至连一个队列的容量都没有。
                            可以简单理解为是一个容量只有1的队列。Executors.newCachedThreadPool使用的是这个队列                           
        4.PriorityBlockingQueue 优先级队列，线程池会优先选取优先级高的任务执行，队列中的元素必须实现Comparable接口

    
五、jvm 类加载器  双亲委派机制
	https://www.cnblogs.com/protected/p/6420128.html

	gc垃圾回收
		cms 回收算法

	内存模型
	
	string常量池存在哪个位置
		1.7（永久代） 和 1.8 分别存在那里

	oom如何排查， 
	cpu100怎么排查解决	jmap， jstack

六、分布式id
	    
	    怎么生成分布式id，怎么保证全剧唯一
    	分布式事务。 （干脆说不会）


七、缓存 和数据库的 一致性
    
	    先清除缓存旧值。-》 change db -> 再异步清缓存
	    最终一致性

八、吞吐量：

        异步
        去锁
        批量  （批量操作）
        复用  （例如：线程池，线程复用）
        解偶

九、dubbo原理

    	consumer 到 produce 的访问流程
	    性能参数设定


十、rpc 设计
	远程调用 超时报错 如何实现；









===== vpn keychain 内容

	name: pritunl
	kind: application password
	account: 2fe7c32dfad3cc3ee29b9c060fbd6d0e
	where: pritunl
	password: PHRscy1hdXRoPgojCiMgMjA0OCBiaXQgT3BlblZQTiBzdGF0aWMga2V5CiMKLS0tLS1CRUdJTiBPcGVuVlBOIFN0YXRpYyBrZXkgVjEtLS0tLQpiY2RhOThjZTlkZmM4Y2I5NmVhMWU1ODJhNDhmZTA0Ngo0NWQ1MDdmNzQwMzdiMjU0ZTE4NWYzOTNiZGIwZmFjYwo3Y2ZhMmMyODY2MWE2OGEyNmQwZTI2MjczYmY4MWFhZAoxZWM0NTRiOTkxMjNhNzlhMmI1NDkwZTZmNTY0MTlkMwo1YjBiNmIzMGM3OGZlMGIzOWVkMjg5ZTM2Y2ZjZjFlNgplNjkyZTAwMDM2MWNiYmM5ZTYwOWU4YWFjZTI4MmJiZAo3YjZkYWU5MTA4Yzg3NGExZjQwNjIxOGUzZTYyZmJmYwpkNjhhNmZkYWEyNzNjYjY1YjFmNWJjNWU2ZjUzOTdmOApjN2NiMDhmZjUzOWVmNzg3NTQ5NzI3NmE3MzBmN2UzNwpmZWM0MWZmZjFmYTkxOGYxM2FkZGExNzEzNGI0OTdjYgo1OGUzYjE0N2U3YzZlMDQwNTZlMWUwZDA4ZTE3M2VhNApmMjEyODc1Yjc5ZGVlYmI1ZjdhODI3MzUwZjZlOTY1NQpkNzViY2JlZjQ2ZWVkOGIyMWY3ZTkxOGVjMzYyYjlmZQo5YTc1Y2M5NTU0NTY0MDM1MGEzOGNjNDg2NTYyODJkNgo3YzhhZmNkN2JmODE3MGUyZWE3MGI5YzEzMDMyMGM1OAoxYWUwNDc2NjQ2Mzg2ZWU0MTUyYzI1YjgxOWU2N2M5MgotLS0tLUVORCBPcGVuVlBOIFN0YXRpYyBrZXkgVjEtLS0tLQo8L3Rscy1hdXRoPgo8a2V5PgotLS0tLUJFR0lOIFBSSVZBVEUgS0VZLS0tLS0KTUlJSlFRSUJBREFOQmdrcWhraUc5dzBCQVFFRkFBU0NDU3N3Z2drbkFnRUFBb0lDQVFEZWJaTFk2eUF4enlXeApON3pKZ0R0QldkVG9RSkp3bnlBMS9vR01kUVVvcTNXL25lU0xhRmtEejVMU1ZYWUIyWWpwTXhhNG8zNTNZQ0t3Cit6Y2U1c1lzcGZUZkpUOFBEb1l6UHZwYUVLNXVnSFExYU1WbENyL1ZOUEJVdGpjN2ZwZW5XeXVoc01rbDJEL08KS0FiajZ4WWJVSjdqcGdKMS9ud1NzZWl6MS9USlRpaU9PUUE0Tyt5d2JCYTZCQ21WdlIxYThrb1ZSK0dTcWs3dAprTTlBUi8xNWxCUlFUSXcwTGFIREh0NzFoREg0aXNWeW1USDROOGk3LzZLb3VKTXVYRzZNZlZCb0pMSEVhS2dCCmhIRVdBVTVSWnhVamUvTDFmUG9hWnZIdHpOMDV1OHdhL1p3VG91S1lFSzU5RnUxeGV5THZuU1FCclZmbTllR1AKMzBqcUg3RVZBaGVRTnlrTy8zUmtXcUgxV20yeVJjZ1RJbzZyd0pSNG9jeXcxZVpRdSsvMGZVeWJWaTBhOVM1MAo3a1JBbU9EaGRaNUlsNk90LzBSYXBlUjRIS1JlaVpSdXN6dTdrcHZPclVXY040VWZ4bXpKUGo5Y2RiZHZJL3RjCnEzWkJNNUtKZmcrRHhHRXVVQStpQzdidHpLS3RqVnI1QjVobUZOeGtvU2R5Nmd3SWZJaUNReUFEQUVHTzdlSFUKZTBwQU55MHRNRnBIc1ZreU5EakJuODAwTmh5UUFjTGxuMmYxd0pvMFc4cEJNakJ5Y0ZFT3M4WnZpVWJza09qRQpzU0hqRTVWSm5TckVlN2d1NkRLMi9uL1MyTkJUU1BJTHd6QklWa1FYTjdocnFMZFlIQXNxY1pIOVRLOEJ1c25ZCjdzTWQrT0V1R05kRldYdzNHYUlDdWN5cnBtK1ZzUUlEQVFBQkFvSUNBRGtTaEl1RVNVNStpSDFnZitCME5zQWQKL0xOYlhqZm5uS3crRVorb3B4bzRaSTQyUlY0LytCY3lPOG9jZzVzVjVqdHdFSFJ2L3hOQ1o2UmgwVkxzaEEwUQp5RFE1cW1TQ3BZeWdyRHhQNjNJUEk4SlFqOVhOWEV1b3VjSUN2MGxVd2pUKy84TU5TWStjWFVMWW9tdjQ5a01yCi9Ka05EN1NYQjk4RUoyRitHQ0ZQSExqaGgxem1nMlVCVnhPRDNlZStEcm8zVVNzRXRxeWJab1V1a3Y4N2VaNm8Ka0UzS1ZtOU5CaVM2MXhySVFNbzFVVlNHV253R1ZFdG8zS1BPT0hXODRlREtoc2N5SXlyeWI2bDBBbEJUSlhWZApyZDdjL3dwZWR3NHZQQVI0Zkl0RDFLdFlSNlFDTjRJdFJaaHpPWm5VcnJJczhBQStLai9vOTJlak0ySmZuMS82Ckk5YlVadUR4N2FlK2JKK1RwY3Fsd1F0bzZpTEpxVFVlQm8xcUNYNGcvdklBTXE2TnFjamt0MXcySVRRUU9tdzMKWjNWeUFETUtlWk1UMTNXV29ONTgySG5lT2cwVkh2MS8wbnQ1RWRCRzRxMFJTdkJxaEhQcFk5YjdkdklaVllCcwp2MlhqVUp5OWpjbWp6dDN5NU9WWitBUXNaNzlBVHhpVWZSWFV5dDNkMGxMRU5weVJ5cmJuNU9SbWFZREpCcEZwCmNSdUhCUVB3Q2JTZ0duYnpQTVhySzViQnhQbk5EenBGZ0k5b3dQT2V4VjVMekNVVGdrSFNhSHdhQTMwcjNwSXgKU3NVRDAxTVp2c1crbTk2UEpCN0gvclNvSjVuUVVDREZ3QXdEd3F4M0cweXp0Znl3UGJxMStLSGU3aHRwT0RNYgptUkZKR0VrTDlqSyswalZOS1pVQkFvSUJBUUR2dWZGQmxJUEZJQVRIbWNDaFFHVVpLVEsrdmVtTm9MWU5nMUpICkFrUVVVSnNJQksrT1k5K1B3MDhkZG9YMXU2eGYydGNHL2tIOGNra25hRVFFaWo5VWtPYjhzTXEvY011VGM3emgKOGJFT0liZUZjWWZQL2hJQnNIaXZQVVpnUVlQWWtLTXJyOG45Vk5JcXVrK1hPcWxDRlZRckxoTkxGd2ozbDRXMwo3TUlXeWdDR0tNNTRzdWJ1M2NkZkpoY1pWNzZqRGdxNGJkUjMzWU5wUUhtU3lGaEFRZDNhZ2dFVVQ3RGRiSzZzCjJTMm5ycWFFb2ZJTDRUNWNKYjVqNGg2dUV1VWVQaFNEMGR2V3NUT3BneHZBSVA5eEZHVW1PdjY4citWdkozT0MKNzNiSGp5bE1zRnRDMjZQOVBoL1lSZTJKbDRmV1hhQllnSGg2TElyQ05iVU10VWhGQW9JQkFRRHRod09zeVJOQQo3VysxekJLZVRUeHZiU3RDbXIveTg4ZFhOdTFKSDF1b1pkSE1hUkVLNHZPUVNNb0diaGRySlVMdzdXTU15T2hSCkQzeTFJVFNjUkM3VXNkUldnM1lUQzR6NzNDeWR0MWIxMzVkaXN3TFlFZ3pKUk11aldTdExDbnJGNU9WdFBMZW4Kay9sRFF6cU8vK0kxNEpac01DeXEzSnN1ZUNSMWxTQkVYd1B6M3hRLzFhWUNBTEtTRUpDWk56RHRPcC84RStCMQp3VHNrRFRzSjBZVldFRGwveVlaTktJdDFyT2RqUEFzUFlPM2o5ako1SlEwaFhMNm01RkR6VmhkSXB5djRnT1FLCnFEL21uemw4Z3NqUjBJeHlKVDRJQXdGM011dnBKbUtGVTBUZnFoZFpFc1h2YmFUQmV4VkpQQkNsTHF1TUtmM2oKYmJHZVhXcU5rTng5QW9JQkFEaC81SUtHVkVBamVienBXZU1uWUN4Nzd6L1JWcmhRaGxmTStabE95V2pZVHBHbgpPUkVnTE5LZlA0ZzhKUHB6anY3UWZ4bGcxNEIrRnd1NG9UOUlyZ0JxWG1aejdIVU9Tb3lvSEg1TURtUzhRWnFyCjFVeUtId2hTNVQ2My9GdzNWTEczOVIwWXcweDNyMGp1dkU5eVBlNGJSTWtnaE4rUHdFZEthUE9lVTJnM2w4UUcKM2hMSVozRW95QkorMGJiaFhVYmt5bFlKWEtnYmh4dEpYTjNoWmh0TzZ0RFREeG9RWmlqcHNvbCtiaW8xR1gzdApCVFVUa3pUNG1GVEdPR0RpaHltSGpDSTlWY2pWM3MwMjMxVjY4WGxmU3krbGUrQlF2SXhrQ2U5MExOUi93MFlWCjBveUJZN2NONjBkM3c3SklDblhWZnpNY3BFaDNNd1R1ejRyOU50a0NnZ0VBTkZldVhhTXAxUUw5dlFndGlpam4KQ243Nks0RkNqMTFkTGtEeVpVeTl2QXhFQVZHZDZkSTliaXc4S05LVjRveWhoY29xSzZVU1hOcVcxcXRzbG9zSAo3dU1KTWM5aWM2djE4NEovYW5uK2RjeEI1Z1FndWxDdjlLSnVaWjYreFppWG1ZaHBibnNVMVo3c0xRQmhwU1dLClNla2wvTTFZNm9ZV3hGVEJaQS9xdm1aWnpxSDRLbUVBWml5VkxVN3RMblpWRjlIMWMyMUY1MjBmaFVCTkpFckIKb1VlT0JEVDZuWUl6eVVqQzdNRk1ocVVMaDFLM3FLNmJpQ0NSK3VFUlhhNDVEMCtYaHZtUVJ6RHdjL2ZlaVN1Zwo2V3VWcDRuQmM2NkY0aWljaUJKblg0NEwvV1hVSTFMWnYzdGY5U3NVYzhFQ3dnUFkxZGhqRkgwVnlSbzQ0dDFyCldRS0NBUUJ1cXA2SXFHNkcxamJmcTA1R2Mrd0FRdjR1dkZYay8rblppOUVkODRnbnJsU2k1M0srQzUvSER2UFEKV0pTbXJKVy95Mkxpdkc0NDdwNXZZU3hUYURmTktDUHY1ZEpTRytvcnk0M0x4SzFSYnN5WHc3Mmh6amxlNGtoaQpQbDlFbVdKK0RINHF1Ly96TkUyWFIzL3hxSzVRVGtRSlN1NTBOSGw4b3ZENHFna1loSS92Rm9CQVBLdUJNWjVsCllsMEsxTzhlNDhHeGV4L3BYMVMzRjBTN2VPR2ZlUHhPb3duVmRxei9zcEQ0ZFd4YjFuM0V4YTk5NkEzR3p5bEcKaDBjc1lsaHUvem5ucG1sZjZIamQycTU0MVF3bHZNWmFIUkUrOS9JSFllODV1aUtBRmdMK0ZIdW9jYUhLaWlqagpKSVo5YlNXM08zaGZVYTR0Wm1acFJ3YnlGRmFECi0tLS0tRU5EIFBSSVZBVEUgS0VZLS0tLS0KPC9rZXk



	










