package com.qunar.homework.Units.Display;

import com.qunar.homework.Units.Interface.DisplayHandler;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/20.
 */
public class CountDisplay implements DisplayHandler{
    public void display(Object o) {
        List<Map.Entry<String,Integer>> listMap = (List<Map.Entry<String,Integer>>)o;
        for(int i=0;i<10 && i<listMap.size();i++) System.out.println(listMap.get(i));
    }
}
