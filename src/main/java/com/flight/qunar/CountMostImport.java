package com.flight.qunar;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 问题：根据指定项目目录下（可以认为是java源文件目录中），统计被import最多的类，前十个是什么。
 *
 * 1. 暂时不考虑“.*”的情况
 * 2. 暂时不考虑静态导入的情况
 * 3. 相同统计次数按照字典序列进行排序
 *
 * Created by 冯麒 on 2016/6/16.
 */
public class CountMostImport {

    static HashMap<String,Integer> importCountMap;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入项目目录的绝对路径:");
        while (scanner.hasNext()) {
            importCountMap = new HashMap<String,Integer>();
            countMostImport(scanner.nextLine());
            System.out.println("输入项目目录的绝对路径:");
        }
    }

    public static void countMostImport(String path) {
        File file = new File(path);
        if(file.isFile()) return;
        //遍历各个文件并进行统计
        listFiles(file);
        //对统计结果进行排序并打印
        List<Map.Entry<String, Integer>> list_data = new ArrayList<Map.Entry<String, Integer>>(importCountMap.entrySet());
        Collections.sort(list_data, new Comparator<Map.Entry<String, Integer>>() {

            public int compare(Map.Entry<String, Integer> s1, Map.Entry<String, Integer> s2) {
                if(s1.getValue() > s2.getValue()) {
                    return -1;
                } else if(s1.getValue() < s2.getValue()) {
                    return 1;
                } else {
                    //相同次数则按照字典序进行排序
                    String temp1 = s1.getKey().substring(s1.getKey().lastIndexOf("."));
                    String temp2 = s2.getKey().substring(s2.getKey().lastIndexOf("."));
                    for(int i=0; i<temp1.length() && i<temp2.length(); i++) {
                        if(temp1.charAt(i) > temp2.charAt(i)) {
                            return -1;
                        } else if(temp1.charAt(i) < temp2.charAt(i)) {
                            return 1;
                        }
                        if(i == temp1.length()-1) return 1;
                        if(i == temp2.length()-1) return -1;
                    }
                }
                return 0;
            }
        });
        System.out.println("import数排行前10的类为:");
        for(int i=0; i<10 && i<list_data.size(); i++) {
            System.out.println(list_data.get(i).getKey());
        }
    }

    public static void listFiles(File file) {
        File[] files = file.listFiles();
        for(File fileName : files) {
            if(fileName.isDirectory()) {
                listFiles(fileName);
            } else {
                String suffix = fileName.getName().substring(fileName.getName().lastIndexOf(".") + 1);
                //判断是否是java文件
                if(suffix.equals("java")) {
                    BufferedReader bufferedReader = null;
                    try {
                        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
                        String readData;
                        while ((readData = bufferedReader.readLine()) != null) {
                            String noSpace = readData.trim();
                            //不遍历类文件
                            if(noSpace.startsWith("public") || noSpace.startsWith("class")
                                    || noSpace.startsWith("private") || noSpace.startsWith("interface")
                                    || noSpace.startsWith("final") || noSpace.startsWith("abstract class")) break;

                            Pattern pattern = Pattern.compile("(import *)(([a-zA-Z]*\\.)*[A-Z][a-zA-Z]*)( *;)");
                            Matcher matcher = pattern.matcher(noSpace);
                            while(matcher.find()) {
                                if(importCountMap.containsKey(matcher.group(2))) {
                                    int count = importCountMap.get(matcher.group(2)) + 1;
                                    importCountMap.put(matcher.group(2), count);
                                } else {
                                    importCountMap.put(matcher.group(2), 1);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if(bufferedReader != null) {
                                bufferedReader.close();
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }
}

