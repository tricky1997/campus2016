package com.qunar.training.CountMostImport;

import java.util.*;

/**
 * Created by shining.cui on 2016/6/7.
 */
public class Sorter {
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
