package com.qunar.homework.EffectiveLines;

import java.io.BufferedReader;
import java.io.File;

/**
 * 作业一：统计一个Java文件的有效行数。（作业命名：EffectiveLines）
 * 1、有效行不包括空行；2、不考虑多行注释
 *
 * 代码思路：
 * 1、根据给定路径读取要分析的源代码文件
 * 2、获取文件的BufferedReader
 * 3、分析源代码的每一行，得到有效行数
 *
 * Created by zhaocai-luo on 2016/6/15.
 */
public class Main {
    public static void main(String[] args){
        // 1、读取文件
        String path  = System.getProperty("user.dir");
        File file = new File(path + "/res/TestClass.java");
        // 2、获取BufferedReader
        BufferedReader bufferedReader = new FileUtil().getBufferedReaderFromFile(file);
        // 3、得到有效行数
        int effectiveLines = new EffectiveLinesCounter().getEffectiveLines(bufferedReader);
        // 4、输出
        System.out.println("源代码文件" + file.getName() + "的有效行数为：\t" + effectiveLines + "行");
    }
}
