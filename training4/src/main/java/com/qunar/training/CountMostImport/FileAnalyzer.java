package com.qunar.training.CountMostImport;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shining.cui on 2016/6/7.
 */
public class FileAnalyzer {
    public static Map<String, Integer> analyzeImportClassesMap(List<File> fileList) throws IOException {
        Map<String, Integer> importClassesMap = new HashMap<String, Integer>();
        BufferedReader bufferedReader;
        for (File file : fileList) {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String trimLine = line.trim();
                if (trimLine.startsWith("import")) {
                    String[] split = trimLine.split(" ");
                    String importClassName = split[1].substring(0, split[1].length()-1);
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
