package com.qunar.homework.CountMostImport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 遍历分析每一个Java文件的引用类信息
 * Created by zhaocai-luo on 2016/6/15.
 */
public class FileAnalyzer {
    /**
     * 遍历分析每一个Java文件的引用类信息
     * @param fileList Java文件列表
     * @return 引用类的统计结果Map
     * @throws IOException 文件读取异常
     */
    public static Map<String, Integer> analyzeImportClasses(List<File> fileList) throws IOException {
        Map<String, Integer> importClassesMap = new HashMap<String, Integer>();
        BufferedReader bufferedReader;

        for (File file : fileList) {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // 去除收尾的空字符
                String trimLine = line.trim();

                // 当以import开头，且接着是空字符时，判断为一条import语句 （避免了误判情况，如importClassesMap）
                if ((trimLine.length() > 0) && trimLine.startsWith("import") && (trimLine.charAt(6) <= ' ')) {
                    if (trimLine.endsWith("*;")){   //  不考虑.*的引用情况
                        continue;
                    }

                    // 得到引用类名
//                    String[] split = trimLine.split(" ");
//                    String importClassName = split[1].substring(0, split[1].length()-1);

                    int st = 6;
                    int len = trimLine.length();
                    while ((st < len) && (trimLine.charAt(st) <= ' ')) {
                        st++;
                    }
                    String importClassName = trimLine.substring(st, len);

                    // 将统计结果放入Map容器
                    if (importClassesMap.containsKey(importClassName)) {
                        Integer importNumber = importClassesMap.get(importClassName);
                        importClassesMap.put(importClassName, importNumber + 1);
                    } else {
                        importClassesMap.put(importClassName, 1);
                    }
                }
            }
        }
        return importClassesMap;
    }
}
