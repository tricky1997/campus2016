package com.qunar.training.CountMostImport;

import java.util.*;

/**
 * 对map进行排序，按照value进行排序
 * Created by shining.cui on 2016/6/7.
 */
public class Sorter {
    /**
     *
     * @param importClassesMap 对import类进行统计的未排序结果
     * @return 对import类进行统计的排序后结果
     */
    public static List<Map.Entry<String, Integer>> sortImportClassMap(Map<String, Integer> importClassesMap) {
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<Map.Entry<String, Integer>>();
        List<Map.Entry<String, Integer>> sortedImportClassList = new ArrayList<Map.Entry<String, Integer>>(importClassesMap.entrySet());
        Collections.sort(sortedImportClassList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                int value1 = o1.getValue();
                int value2 = o2.getValue();
                return value2 - value1;
            }
        });
        Iterator<Map.Entry<String, Integer>> iterator = sortedImportClassList.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> next = iterator.next();
            sortedList.add(next);
        }
        return sortedList;
    }
}
