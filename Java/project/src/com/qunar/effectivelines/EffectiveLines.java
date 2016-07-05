package com.qunar.effectivelines;

import java.io.*;

/**
 * Created by Charmy on 2016/6/27.
 * 统计一个java文件的有效行
 */
public class EffectiveLines {

    /**
     * 统计java文件的有效行，考虑单行注释不考虑多行注释及空白行
     */
    public  int countlines(String filNname){
        int linesNumber=0;
        BufferedReader  reader=null;
        try {
            reader =new BufferedReader(new FileReader(new File(filNname)));
            String line="";
            while((line=reader.readLine())!=null){
                line=line.trim();
                if(!line.isEmpty()&&!line.startsWith("//"))
                    linesNumber++;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return linesNumber;
    }


}
