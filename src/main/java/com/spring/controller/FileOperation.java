package com.spring.controller;

import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by zhuping on 8/10/2018.
 */

@Component
public class FileOperation {

    public void splitFile(String splitString, String filePath, int lineNumber){

        Reader reader = null;
        LineNumberReader lineNumberReader = null;
        File file = new File(filePath);
        BufferedWriter bw = null;
        int count = 0;
        int count1 = 0;
        if(file.exists()){
            try {

                reader = new FileReader(filePath);
                lineNumberReader = new LineNumberReader(reader);
                String line;
                boolean flag = false;
                while ((line = lineNumberReader.readLine())!=null){



                    if(count % lineNumber == 0){
                        count1 ++ ;
                        if(bw != null){
                            bw.close();
                        }
                        if(line.startsWith(splitString) && flag != true ){
                            flag = true;
                            if(bw != null){
                                bw.close();
                                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("C:\\docker\\Postgresql\\split_"+ count1))));
                            }
                        }
                        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("C:\\docker\\Postgresql\\split_"+ count1))));
                    }
                    count ++;
                    bw.write(line);
                    bw.newLine();

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(bw != null || reader != null || lineNumberReader !=null){
                        bw.close();
                        reader.close();
                        lineNumberReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }



}
