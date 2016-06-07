package com.qunar.training.CountMostImport;

import java.util.List;
import java.util.Map;

/**
 * Created by shining.cui on 2016/6/7.
 */
public class Display {
    public static void show(List<Map.Entry<String, Integer>> list) {
        if (list.size() > 0) {
            Map.Entry<String, Integer> firstClass = list.get(0);
            System.out.println("被引用次数最多的类为:" + firstClass.getKey() + ",\t被引用次数为:" + firstClass.getValue());
        }
        if (list.size() >= 10) {
            System.out.println("被引用次数前10名的类信息为：");
            for (int i = 0; i < 10; i++) {
                System.out.println("被引用次数第" + (i + 1) + "多的类为:" + list.get(i).getKey() + ",\t被引用次数为:" + list.get(i).getValue());
            }
        } else {
            System.out.println("被引用类个数不足10个，不予显示");
        }
    }
}
