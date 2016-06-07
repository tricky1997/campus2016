package com.qunar.training.CountMostImport;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 三、根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么。（作业命名为：CountMostImport）
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
