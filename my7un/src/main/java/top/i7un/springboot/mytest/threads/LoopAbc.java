package top.i7un.springboot.mytest.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Noone on 2020-08-06.
 */
class resource{
    private int number = 1;
    ReentrantLock reentrantLock = new ReentrantLock();
    Condition condition1 = reentrantLock.newCondition();
    Condition condition2 = reentrantLock.newCondition();
    Condition condition3 = reentrantLock.newCondition();

    public void printA() {
        reentrantLock.lock();
        try {
            //判断
            while (number != 1){
                condition1.await();
            }
            //干活
            System.out.println("A");
            number =2 ;
            //通知
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void printB() {
        reentrantLock.lock();
        try {
            //判断
            while (number != 2){
                condition2.await();
            }
            //干活
            System.out.println("B");
            number =3 ;
            //通知
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void printC() {
        reentrantLock.lock();
        try {
            //判断
            while (number != 3){
                condition3.await();
            }
            //干活
            System.out.println("C");
            number =1 ;
            //通知
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }
}

public class LoopAbc {

    public static void main(String[] args) {
        resource resource = new resource();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                resource.printA();
            }
        }, String.valueOf("a")).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                resource.printB();
            }
        }, String.valueOf("b")).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                resource.printC();
            }
        }, String.valueOf("c")).start();
    }
}
