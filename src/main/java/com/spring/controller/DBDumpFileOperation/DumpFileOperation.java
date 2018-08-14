package com.spring.controller.DBDumpFileOperation;

/**
 * Created by zhuping on 8/9/2018.
 */
public interface DumpFileOperation {

    void cleanDumpFile(String inputPath, String outputPath1, String outputPath2);

    boolean createTableBatch(String createTablePath);

    void insertSqlBatch(String insertSqlBatchPath);

}
