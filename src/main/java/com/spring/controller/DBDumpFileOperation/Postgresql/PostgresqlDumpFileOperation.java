package com.spring.controller.DBDumpFileOperation.Postgresql;

import com.spring.controller.DBDumpFileOperation.DumpFileOperation;
import com.spring.controller.DBDumpFileOperation.Path;
import com.spring.controller.FileOperation;
import com.spring.controller.ThreadPool.ThreadPollManager;
import com.spring.repository.domain.DBOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by zhuping on 8/9/2018.
 */
@Service
public class PostgresqlDumpFileOperation implements DumpFileOperation {

    public static void main(String []args){
        PostgresqlDumpFileOperation postgresqlDumpFileOperation = new PostgresqlDumpFileOperation();
   //     postgresqlDumpFileOperation.cleanDumpFile("C:\\docker\\Postgresql\\ucmdb-fullpackage.SQL","C:\\docker\\Postgresql\\ucmdb-fullpackage-create.SQL","C:\\docker\\Postgresql\\ucmdb-fullpackage-insert.SQL");
        postgresqlDumpFileOperation.splitInsertFile();
    }


//    @Autowired
//    private FileOperation fileOperation;

    public void splitInsertFile(){
        FileOperation fileOperation = new FileOperation();
        fileOperation.splitFile("CREATE", "C:\\docker\\Postgresql\\1.txt",500000);
    }

    @Autowired
    private DBOperation dbOperation;

    @Autowired
    private ThreadPollManager threadPollManager;

    @Autowired
    Path path;

    @Override
    public void cleanDumpFile(String inputPath, String outputPath1, String outputPath2) {
        File file = new File(inputPath);
        Reader reader = null;
        LineNumberReader lineNumberReader = null;
        StringBuffer stringBuffer = null ;
        BufferedWriter bw1 = null;
        BufferedWriter bw2 = null;

        if(file.exists()){
            try {
                reader = new FileReader(inputPath);
                lineNumberReader = new LineNumberReader(reader);
                bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputPath1))));
                bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputPath2))));
                String line;
                int count = 0;
                boolean split = false;

                while ((line = lineNumberReader.readLine())!= null){
                    if(line.equals("") || line.startsWith("--")){
                        continue;
                    }

                    if(line.startsWith("INSERT INTO")){
                        split = true;
                    }

                    if(!split){
                        count ++;
                        if((line.startsWith("ALTER")||line.startsWith("CREATE")||line.startsWith("SET")||line.startsWith("SELECT")||line.startsWith("COMMENT"))&&line.endsWith(";")){
                            bw1.write(line);
                            bw1.newLine();
                        }else if(!line.endsWith(";")){
                            if(stringBuffer == null){
                                stringBuffer = new StringBuffer();
                            }
                            if(line.equals("UNION ALL")){
                                stringBuffer.append(" ");
                            }
                            stringBuffer.append(line);
                        }else {
                            bw1.write(String.valueOf(stringBuffer.append(line)));
                            bw1.newLine();
                            stringBuffer = new StringBuffer();
                        }
                    }
                    else {
                        if((line.startsWith("INSERT INTO") || line.startsWith("CREATE INDEX") || line.startsWith("CREATE UNIQUE INDEX") || line.startsWith("ALTER TABLE"))&& line.endsWith(");") ){
                            bw2.write(line);
                            bw2.newLine();
                        }else if(!line.endsWith(");")){
                            if(stringBuffer == null){
                                stringBuffer = new StringBuffer();
                            }
                            stringBuffer.append(line);
                        }else {
                            bw2.write(String.valueOf(stringBuffer.append(line)));
                            bw2.newLine();
                            stringBuffer = new StringBuffer();
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    reader.close();
                    lineNumberReader.close();
                    bw1.close();
                    bw2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean createTableBatch(String createTablePath) {
        boolean result = dbOperation.operationSQL(path.getCreateTablePath(),1000);
        return result;
    }

    @Override
    public void insertSqlBatch(String insertSqlBatchPath) {
        for(int i = 1; i<=10 ;i++){
            InsertSqlBatchThread insertSqlBatchThread = new InsertSqlBatchThread();
            insertSqlBatchThread.setName("InsertSqlBatch :" + i);
            threadPollManager.getPool().execute(insertSqlBatchThread);
        }
    }


    private class InsertSqlBatchThread extends Thread{

        public void run(){
            System.out.println(Thread.currentThread().getName() +" Thread Start ");
            dbOperation.operationSQL(path.getInsertSqlPath(), 10000);
        }
    }

}
