package com.spring.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhuping on 8/10/2018.
 */
public class SqlExecutor {

    private ExecutorService pool = Executors.newFixedThreadPool(20);

//    public void dumpFileOperation(){
//        pool.execute();
//    }


}
