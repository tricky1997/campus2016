package com.qunar.countMostImport;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Charmy on 2016/6/29.
 * 功能：统计指定目录下被引用次数最多的前十个类
 */
public class CountMostImport {
    HashMap<String, Integer> recordclass;//记录引用类名及次数
    public CountMostImport(String dirName){
        recordclass = new HashMap<String, Integer>();
        File file=new File(dirName);
        getFile(file);
    }
    //递归分析目录中的java源文件
    public void getFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File tempFile : files) {
                getFile(tempFile);
            }
        } else {
            processImportClass(file);
        }
    }
    //统计java源文件中引用的类
    public void processImportClass(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                //当遇到类时，停止读入
                if (line.startsWith("public") || line.startsWith("class"))
                    break;
                //不统计import static 和 带*的引用
                if (line.startsWith("import") && !line.startsWith("import static") && line.charAt(line.length() - 1) != '*') {
                    String className=line.substring(6).trim();
                    Integer count=recordclass.get(className);
                    if(count==null)
                        recordclass.put(className,1);
                    else
                        recordclass.put(className,count+1);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 排序
    public ArrayList<Map.Entry<String,Integer>> getTop10MostImportClass(){
        ArrayList<Map.Entry<String,Integer>> list= new ArrayList<>(recordclass.entrySet()) ;
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
                      @Override
                      public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                          if (o1.getValue()<o2.getValue())
                                  return 1;
                          if (o1.getValue()>o2.getValue())
                                  return -1;
                          return 0;
                      }
                  }

        );
        return list;
    }

}
