package com.ys.qunar.home_work;

import java.io.*;
import java.util.*;

/**
 * Created by shuang on 2016/6/15.
 * 根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么。
 * 如果类的import的次数相同，类名按照字典升序排列。
 */
public class CountMostImport {

    HashMap<String,Integer> importClassCount;//导入类计数
    public CountMostImport(String filePath){
        importClassCount = new HashMap<>();
        this.processFile(new File(filePath));
    }

    public void statisticClass(File file){
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        try {
            while((line=br.readLine())!=null){
                if(line.startsWith("public")||line.startsWith("class"))
                    break;
                if(line.startsWith("import")){
                    line = line.substring(6,line.length()-1).trim();
                    if(line.endsWith("*")||line.startsWith("static"))
                        continue;
                    Integer count = importClassCount.get(line);
                    if(count==null){
                        importClassCount.put(line,1);
                    }else{
                        importClassCount.put(line,count+1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void processFile(File file){
        if(!file.isDirectory())
            statisticClass(file);
        else{
            File[] files = file.listFiles();
            for(File tmpFile:files)
                processFile(tmpFile);
        }
    }
    public void countMostTenImport(){
        List<Map.Entry<String,Integer>> list = new ArrayList<>(importClassCount.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                //降序排列
                if(o1.getValue()<o2.getValue())
                    return 1;
                else{
                    if (o1.getValue()==o2.getValue()){//导入次数相同时
                        return o1.getKey().compareTo(o2.getKey());
                    }
                    return -1;
                }
            }
        });
        //打印输出统计次数前十或全部的导入类
        for (int i = 0; i < (list.size()>10?10:list.size()); i++) {
            System.out.print(list.get(i)+";");
        }
    }
    public static void main(String[] args){
        String filePath = "F:\\java\\git_work_space\\hello-world\\src\\com\\ys";
        CountMostImport csi = new CountMostImport(filePath);
        csi.countMostTenImport();

    }
}
