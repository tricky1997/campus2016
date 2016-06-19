package com.qunar.homework.CountMostImport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 按要求对引用类的统计结果进行输出
 * Created by zhaocai-luo on 2016/6/15.
 */
public class Displayer {
    /**
     * 对统计结果进行排序，排序结果首先按引用次数排序，引用次数相同时按类名的字典序排序
     * 算法处理过程：首先对统计结果按字典序排序，然后按引用次数排序
     * @param list 输入引用类统计结果Map
     * @return 输出排序之后的List
     */
    public static List<Map.Entry<String, Integer>> sortImportClassesMap(Map<String, Integer> list){
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<Map.Entry<String, Integer>>(list.entrySet());

        // 根据引用类名按字典序升序排序
        Collections.sort(sortedList, new Comparator<Map.Entry<String, Integer>>(){

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        } );

        // 根据引用次数再次按降序排序
        Collections.sort(sortedList, new Comparator<Map.Entry<String, Integer>>(){

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        } );

        return sortedList;
    }

    /**
     * 按要求输出统计结果
     * @param importClassesMap
     */
    public static void output(Map<String, Integer> importClassesMap){

        List<Map.Entry<String, Integer>> sortedList = sortImportClassesMap(importClassesMap);

        if (sortedList.size() > 0) {
            Map.Entry<String, Integer> mostImportClass = sortedList.get(0);
            System.out.println("引用次数最多的类是:\n\t" + mostImportClass.getKey() +
                                "\t引用次数:\t" + mostImportClass.getValue());
        }

        if (sortedList.size() >= 10) {
            System.out.println("引用次数前10名的类信息:");
            for (int i = 0; i < 10; i++) {
                System.out.println( "\t排名:\t" + (i + 1)+
                                    "\t引用次数:\t" + sortedList.get(i).getValue() +
                                    "\t类名:\t" + sortedList.get(i).getKey() );
            }
        }
        else if (sortedList.size() > 0){
            System.out.println("引用类个数小于10个，分别是:");
            for (int i = 0; i < sortedList.size(); i++) {
                System.out.println( "\t排名:\t" + (i + 1)+
                                    "\t引用次数:\t" + sortedList.get(i).getValue() +
                                    "\t类名:\t" + sortedList.get(i).getKey() );
            }
        }
        else {
            System.out.println("项目中没有引用任何类！");
        }
    }
}
