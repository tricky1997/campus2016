package com.qunar.homework.Units.After;

import com.qunar.homework.Units.Interface.AfterHandler;

import java.util.*;

/**
 * Created by Administrator on 2016/6/20.
 */
public class AfterWorkSortMap implements AfterHandler{
    public Object getWorkDone(Object o) {
        Map<String,Integer> classMap = (Map<String,Integer>)o;
        List<Map.Entry<String,Integer>> listMap= new ArrayList<Map.Entry<String,Integer>>(classMap.entrySet());
        Collections.sort(listMap, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if(o1.getValue()==o2.getValue()){
                    return o1.getKey().compareTo(o2.getKey());
                }
                else{
                    return o2.getValue()-o1.getValue();
                }
            }
        });
        return listMap;
    }

}
