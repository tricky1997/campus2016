package com.qunar.homework.Units.File;

import com.qunar.homework.Units.Interface.FileHandler;
import com.qunar.homework.Units.Interface.WorkHandler;

import java.io.*;
import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public class FilesParser implements FileHandler{
    public Object getWorkDone(List<File> fileList, WorkHandler workHandler) throws IOException {
        for(File file : fileList){
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(file),"utf-8"));
                String line;
                while((line = bufferedReader.readLine())!=null && workHandler.getWorkDone(line)){

                }
            }
            catch (FileNotFoundException e){
                System.out.println(e);
            }
            catch (UnsupportedEncodingException e){
                System.out.println(e);
            }
            catch (IOException e){
                System.out.println(e);
            }
            finally {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return workHandler.getData();
    }
}
