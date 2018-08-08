package com.spring.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by zhuping on 8/8/2018.
 */
@Repository
public class DbPool {
    private static DataSource dataSource;

    @Autowired
    private BasicDataSource basicDataSource;


    public BasicDataSource createPool(){
        return basicDataSource;
    }

//    public Connection getConnection(){
//        Connection conn = null;
//        try {
//            conn =basicDataSource.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return conn;
//    }

}
