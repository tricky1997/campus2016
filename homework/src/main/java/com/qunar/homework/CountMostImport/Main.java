package com.qunar.homework.CountMostImport;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 作业三：根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么？（作业命名为：CountMostImport）
 *
 * 代码思路：
 * 1、递归读取指定项目目录下的所有java文件对象，保存到一个List容器中
 * 2、遍历每一个java文件，统计每个类为引用的次数，用一个Map保存
 * 3、统计结果输出，首先按引用次数排序，引用次数排名相同时按引用类的字母序排序
 * Created by zhaocai-luo on 2016/6/15.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // 1、读取指定项目路径的所有java文件
        String basicFilePath = System.getProperty("user.dir");
        List<File> files = FileReader.getAllFilesPath(basicFilePath);
        // 2、遍历分析每个文件，并统计类的引用次数
        Map<String, Integer> importClassesInfo = FileAnalyzer.analyzeImportClasses(files);
        // 3、按要求，输出统计结果
        Displayer.output(importClassesInfo);
    }
}
