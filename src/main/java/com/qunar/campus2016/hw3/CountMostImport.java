package com.qunar.campus2016.hw3;

import java.io.*;
import java.util.*;

/**
 * Created by Nicklaus on 4/7/2016.
 */
public class CountMostImport {

    private static String srcPath = "src";
    public static void main(String[] args) {

        //获取某个目录下的所有Java文件
        JavaFiles jf = new JavaFiles();
        jf.getFileLists(new File(srcPath));
        List<String> javaFiles = jf.getFileLists();

        //统计类的出现次数
        Map<String, Integer> map = new HashMap<String, Integer>();

        BufferedReader bf = null;
        for (String str : javaFiles) {
            try {
              bf = new BufferedReader(
                      new FileReader(
                              new File(str)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //读取文件的每一行
            String curline;
            try {
                while((curline = bf.readLine()) != null)
                {
                    if(curline.trim().startsWith("import") && !(curline.endsWith(".*;"))) {
                        String [] split = curline.trim().split(" ");
                        String filePath = split[1];
                        String importCls = filePath.trim().substring(0, split[1].trim().length() - 1);
                        if (map.containsKey(importCls)) {
                            map.put(importCls, map.get(importCls) + 1);
                        } else {
                            map.put(importCls, 1);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //对map进行排序
        List<Map.Entry<String, Integer>> infoIds =
                new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                int value = (o2.getValue()).compareTo(o1.getValue());
                //如果import次数相等则按类名字典序
                if(value == 0) {
                    return (o1.getKey()).compareTo(o2.getKey());
                } else {
                    return value;
                }
            }
        });

        //按次序输出前10个
        int curCount = 0;
        while(curCount < 10 && curCount < infoIds.size())
        {
            Map.Entry<String, Integer> entry = infoIds.get(curCount);
            System.out.println(entry.getKey() + " " + entry.getValue());
            curCount ++;
        }


    }
}
