package top.i7un.springboot.mytest.threads;

import java.util.concurrent.Semaphore;

/**
 * Created by Noone on 2020-08-06.
 */
public class ThreadTest {

    public static void main(String[] args) {
        //三个车位
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire(); //获得这个资源
                    System.out.println(Thread.currentThread().getName()+"或得到这个资源");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName()+"shifangle这个资源");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();//释放掉这个资源
                }
            }, String.valueOf(i)).start();
        }
    }
}
