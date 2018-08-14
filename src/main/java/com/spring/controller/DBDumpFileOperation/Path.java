package com.spring.controller.DBDumpFileOperation;

import org.springframework.stereotype.Component;

/**
 * Created by zhuping on 8/14/2018.
 */

@Component
public class Path {
    private String createTablePath;

    private String insertSqlPath;


    public String getCreateTablePath() {
        return createTablePath;
    }

    public void setCreateTablePath(String createTablePath) {
        this.createTablePath = createTablePath;
    }

    public String getInsertSqlPath() {
        return insertSqlPath;
    }

    public void setInsertSqlPath(String insertSqlPath) {
        this.insertSqlPath = insertSqlPath;
    }
}
