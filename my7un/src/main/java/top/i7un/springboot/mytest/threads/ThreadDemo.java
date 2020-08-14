package top.i7un.springboot.mytest.threads;

import java.util.concurrent.*;

/**
 * Created by Noone on 2020-08-06.
 */

class MyThread implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        return null;
    }
}
public class ThreadDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //这里最好用ExecutorService  他有shutdown方法
//        ExecutorService executor = Executors.newFixedThreadPool(5);//固定线程数的线程池
//        ExecutorService executor = Executors.newSingleThreadExecutor();//只有一个线程的线程池
        ExecutorService executor = Executors.newCachedThreadPool();//一个池子有N个线程

        try {
            for (int i = 0; i < 10 ;i++) {
                executor.execute(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //注意关闭
            executor.shutdown();
        }

    }

    private static void callableDemo() throws InterruptedException, ExecutionException {
        MyThread myThread = new MyThread();
        FutureTask<Integer> integerFutureTask = new FutureTask<>(myThread);
//        FutureTask 是runable的子类 里面可以传一个 Callable  相当于一个适配器
        new Thread(integerFutureTask, "A").start();
        System.out.println(integerFutureTask.get()); //获取callable计算结果 直到获取完成
//        System.out.println(Runtime.getRuntime().availableProcessors()); //查看有多少核
    }

}
