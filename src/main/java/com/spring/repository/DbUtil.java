package com.spring.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by zhuping on 8/8/2018.
 */
@Repository
public class DbUtil {
    @Autowired
    private DbPool dbPool;

    public Connection getConnection(){
        BasicDataSource basicDataSource = dbPool.createPool();
        Connection conn = null;
        try {
            conn = basicDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
