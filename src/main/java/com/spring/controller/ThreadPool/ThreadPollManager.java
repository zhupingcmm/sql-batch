package com.spring.controller.ThreadPool;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhuping on 8/13/2018.
 */
@Component
public class ThreadPollManager {
    private final ExecutorService pool;

    public ThreadPollManager() {
        this.pool = Executors.newFixedThreadPool(20);
    }


    public ExecutorService getPool(){
        return pool;
    }

    static class SqlThread extends Thread{

        public void run(){
            System.out.println("sdfdsfd");
        }
    }

    public static void main(String[]args){
        ThreadPollManager threadPollManager =new ThreadPollManager();
        ExecutorService pool = threadPollManager.getPool();
        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
            }
        });
        SqlThread sqlThread = new SqlThread();
        sqlThread.setName("sql");
        pool.execute(sqlThread);
//        pool.shutdown();
    }

}
