package com.CountMostImport;

import java.io.*;
import java.util.*;


/**
 * 递归读取目录下所有文件，统计import的类的个数
 * 这个版本没考虑“import java.io.*”这种带*号的情况，只是将这种情况视作一个类来看待。
 * 如果“import java.io.*”排名第9，且没有其它io包的类被引用。如何确定io包有哪几个类占据第9，第10？
 */
public class CountMostImport {
    private HashMap<String , Integer> hashMap = new HashMap<String , Integer>();  //将所有类放到该字典里统计

    /*
     * 递归读取目录下所有文件
     */
    public void getfile(String filename) {
        File file = new File(filename);
        if (file.isDirectory()) {
            File []files = file.listFiles();  //查看子目录下所有文件
            for (File ff : files) {
                this.getfile(String.format("%s", ff));  //递归调用子目录下文件
            }
        }
        if (file.isFile()){
            countImport(String.format("%s", file));  //调用读取文件的函数
        }
    }

    /*
     * 统计文件中被引用的类的个数
     */
    public void countImport(String filename) {
        try {
            FileReader fin = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fin);  //打开文件

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("import")) {  //如果文件是引用类语句
                    int begin = 6;
                    while (line.charAt(begin) == ' ') {
                        ++begin;
                    }
                    String classpath = line.substring(begin,line.length()-1);
                    if (hashMap.containsKey(classpath)) {  //字典中已经有这个类了，次数+1
                        int value = hashMap.get(classpath);
                        value++;
                        hashMap.put(classpath,value);
                    } else {  //字典中没有这个类，把这个类加入字典中
                        hashMap.put(classpath,1);
                    }
                }
                //如果遇到类声明了，则确认import语句块已经结束
                if (line.startsWith("public") || line.startsWith("class")) {
                    break;
                }
            }
            bufferedReader.close();
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 字典按值排降序，将前十输出
     */
    public void sortAndPrintTop10() {
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(hashMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o1.getValue() == o2.getValue()) {
                    return o1.getKey().compareTo(o2.getKey());  //如果次数一样，
                }
                return o1.getValue() - o2.getValue();  //降序排列
            }
        });
        int cursor = 0;  //计数器，只输出前十
        for (Map.Entry<String, Integer> map: list) {
            System.out.println(map.getKey()+"  :  "+map.getValue());
            cursor++;
            if (cursor >= 10) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        CountMostImport countMostImport = new CountMostImport();
        if (args.length < 1) {
            System.out.println("this program need 1 arguments");
            System.exit(0);  //以后看情况决定是否选1
        }
        countMostImport.getfile(args[0]);  //输入文件名
        countMostImport.sortAndPrintTop10();
    }
}
