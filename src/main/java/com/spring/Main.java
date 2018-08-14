package com.spring;

import com.spring.controller.ThreadPool.ThreadPollManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

/**
 * Created by zhuping on 8/8/2018.
 */
public class Main {
    public static void main(String[]args) throws SQLException {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-dbConnection.xml");
//        PostgresqlDumpFileOperation postgresqlDumpFileOperation = ctx.getBean(PostgresqlDumpFileOperation.class);
//        postgresqlDumpFileOperation.cleanDumpFile("1.txt","C:\\docker\\Postgresql\\ucmdb-fullpackage-create.SQL","C:\\docker\\Postgresql\\ucmdb-fullpackage-insert.SQL");
//        postgresqlDumpFileOperation.cleanDumpFile("C:\\docker\\Postgresql\\ucmdb-fullpackage.SQL","C:\\docker\\Postgresql\\ucmdb-fullpackage-create.SQL","C:\\docker\\Postgresql\\ucmdb-fullpackage-insert.SQL");

        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-ThreadPool.xml");
        ThreadPollManager manager = (ThreadPollManager) ctx.getBean("threadPollManager");
        manager.getPool().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("12");
            }
        });

    }
}
