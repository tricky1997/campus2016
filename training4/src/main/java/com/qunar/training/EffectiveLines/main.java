package main.java.com.qunar.training.EffectiveLines;

import java.io.BufferedReader;
import java.io.File;

/**
 * 一、统计一个Java文件的有效行数。（作业命名：EffectiveLines）
 1、有效不包括空行
 2、不考虑代码见有多行注释的情况
 *
 * Created by shining.cui on 2016/6/5.
 */
public class Main {
    public static void main(String[] args) {
        FileLineCounter fileLineCounter = new FileLineCounter();
        File file = fileLineCounter.getFile();
        BufferedReader bufferedReader = fileLineCounter.getBufferedReader(file);
        int effectiveLines = fileLineCounter.getEffectiveLines(bufferedReader);
        System.out.println("文件有效行数为:" + effectiveLines + "行");
    }
}
