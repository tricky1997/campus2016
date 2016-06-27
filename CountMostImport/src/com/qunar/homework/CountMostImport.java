package com.qunar.homework;

import   java.io.*;
import java.util.*;


//以下import类作为测试用
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Executable;
import java.lang.reflect.Executable;
import java.lang.reflect.Executable;
import java.lang.Integer;
import java.lang.Integer;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.Set;

/**
 * Created by Jerry on 6/26/2016.
 */
public class CountMostImport {

    public static void main(String[] args) {

        //获取目录下的所有Java文件
        String add = "src";
        File file = new File(add);
        JavaFiles jf = new JavaFiles();
        jf.getFileLists(file);
        List<String> javaFiles = jf.getFileLists();

        //统计类与次数键值对
        Map<String,Integer> map = new HashMap<String,Integer>();


        BufferedReader bf=null;
        for (String str :  javaFiles) {
           // System.out.println("args = [" + str + "]");
            File javaFile = new File(str);
            try {
              bf = new BufferedReader(new FileReader(new File(str)));
            } catch (FileNotFoundException e) {
                System.out.println("读入文件错误...");
            }

            String line = null;
            try {
                while((line=bf.readLine())!=null)
                {
                    if(line.trim().startsWith("import")&&!(line.endsWith("*;")))
                    {
                        String [] split = line.trim().split(" ");
                        System.out.println("args = [" + split[1] + "]");
                        String importClass = split[1].trim().substring(0,split[1].trim().length()-1);
                        Integer count = map.get(importClass);
                        map.put(importClass,count==null?1:count+1);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        //sort
        List<Map.Entry<String, Integer>> infoIds =
                new ArrayList<>(map.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                int value = (o2.getValue()).compareTo(o1.getValue());
                if(value==0)   //如果import次数相等则按类名字典序
                    return (o1.getKey()).compareTo(o2.getKey());
                else
                    return value;
            }
        });


        //按次序输出
        int freqRank = 0;
        while(freqRank<10&&freqRank<infoIds.size())
        {
            Map.Entry<String, Integer> entry = infoIds.get(freqRank);
            String className = entry.getKey();
            System.out.println(className+"   "+entry.getValue());

            freqRank++;
        }


    }
}
