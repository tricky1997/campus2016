package com.ys.qunar.home_work;

import java.io.*;

/**
 * Created by shuang on 2016/6/14.
 * 统计Java文件的有效行数
 * 要求：
 * 1.有效行数不包括空行
 * 2.不考虑代码见有多行注释的情况
 * 理解:空行、单行注释(//、/*
 */
public class EffectiveLines {

    public int effectiveLines(File file) {
        //判断Java文件是否存在
        if (!file.exists()||file.isDirectory())
            return -1;
        String line;
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if ((line.length() == 0)||line.startsWith("//")||
                        (line.startsWith("/*")&&line.endsWith("*/")))
                    continue;
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void main(String[] args) {
        File file = new File("F:\\java\\codes\\MyWebServer.java");
        EffectiveLines el = new EffectiveLines();
        int count = el.effectiveLines(file);
        System.out.print(count);
    }
}
