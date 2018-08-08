package com.spring;

import com.spring.repository.DbUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

/**
 * Created by zhuping on 8/8/2018.
 */
public class Main {
    public static void main(String[]args) throws SQLException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-dbConnection.xml");
        DbUtil dbUtil = ctx.getBean(DbUtil .class);
        System.out.println(dbUtil.getConnection());
        dbUtil.getConnection();


    }
}
