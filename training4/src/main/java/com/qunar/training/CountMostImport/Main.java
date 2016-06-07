package com.qunar.training.CountMostImport;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 三、根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么。（作业命名为：CountMostImport）
 *
 * 本题思路：
 * 1、根据文件夹地址，通过递归读取得到所有文件对象，为了减少IO阻塞，所有的文件对象都存放在内存中。
 * 2、便利文件列表，挨个分析文件。获得被import的类名称，并统计次数。使用map进行保存，key为名字，value为次数
 * 3、对import的统计结果map进行排序，按照value的排序结果存放到list中，为了方便适应不同显示规则显示结果
 * 4、按照题目要求显示
 * Created by shining.cui on 2016/6/6.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String basicFilePath = "F:\\gitRepo\\TMP";
        List<File> files = FileUtil.readAllFiles(basicFilePath);
        Map<String, Integer> importClassesInfo = FileAnalyzer.analyzeImportClassesMap(files);
        List<Map.Entry<String, Integer>> sortedImportClassMap = Sorter.sortImportClassMap(importClassesInfo);
        Display.show(sortedImportClassMap);
    }
}
