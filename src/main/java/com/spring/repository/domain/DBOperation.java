package com.spring.repository.domain;

import com.spring.repository.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by zhuping on 8/8/2018.
 */
@Repository
public class DBOperation {

    @Autowired
    private DbUtil dbUtil;


    public boolean createTable(){


        return false;
    }



    public boolean operationSQL(String filePath , int batchLineNumber){
        boolean result = false;
        long start = System.currentTimeMillis();
        Connection conn = null;
        Statement statement = null;
        try {
            conn = dbUtil.getConnection();
            statement = conn.createStatement();

            File file = new File(filePath);
            LineNumberReader lineNumberReader = null;

            if(file.exists()){
                try {
                    lineNumberReader = new LineNumberReader(new FileReader(file));

                    String line ;
//                    StringBuffer stringBuffer = new StringBuffer();
                    int batchCount = 1000;
                    int count = 0;
                    while ((line = lineNumberReader.readLine())!= null){
                        statement.addBatch(line);
                        count ++;
                        if(count % batchLineNumber == 0){
                            statement.executeBatch();
                            statement.clearBatch();
                            System.out.println(Thread.currentThread().getName() + " count : " + count);
                        }

//                        if(count % 100000 == 0){
//                            System.out.println("Take time : " + (System.currentTimeMillis() - start)/1000);
//                        }
                    }

                    statement.executeBatch();
                    statement.clearBatch();
                    result = true;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        lineNumberReader.close();
                        statement.close();
                        conn.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            result = false;
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println("Total take time: " + (end - start)/1000);
        return result;
    }

}
