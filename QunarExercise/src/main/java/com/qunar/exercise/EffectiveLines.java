package com.qunar.exercise;

import java.io.*;

/**
 * 统计java文件中的有效行数
 * 1.有效行数不包括空行
 * 2.不考虑代码间有多行注释的情况
 */
public class EffectiveLines {

    public static void main(String[] args){

        String filePath = "src/main/resources/test.java" ;
        int lines = new EffectiveLines().CountEffectiveLines(filePath) ;
        System.out.println("有效行数：" + lines);

    }

    /**
     * 统计Java文件中有效行数
     * @param filePath Java文件的路径
     * @return 有效行数
     */
    public int CountEffectiveLines(String filePath){
        // 字节流
        FileInputStream fis = null ;
        // 将字节流转换为字符流
        InputStreamReader isr = null ;
        // 缓冲读文件，包装InputStreamReader，提高读文件的性能。
        BufferedReader br = null ;
        // 创建File类
        File inputFile = new File(filePath);
        int LineCount = 0 ;

        try {
            // 从文件类转换为字节流
            fis = new FileInputStream(inputFile);
            // 将字节流转换为字符流
            isr = new InputStreamReader(fis);
            // 用BufferedReader包装字符流,用于一行一行地读取文件
            br = new BufferedReader(isr);
            String str = null ;
            while((str = br.readLine()) != null){
                // 若该行是以 “//” 开头 或者 为空行 则该行为非有效行
                if(str.trim().startsWith("//") || str.trim().equals(""))
                    continue;
                LineCount++ ;

            }

            //return LineCount ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭文件流
            try {
                if(br != null)
                    br.close();
                if(isr != null)
                    isr.close();
                if(fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return LineCount;
    }
}
