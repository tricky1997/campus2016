package com.qunar.homework.Units.Display;

import com.qunar.homework.Units.Interface.DisplayHandler;

/**
 * Created by Administrator on 2016/6/20.
 */
public class EffectDisplay implements DisplayHandler{
    public void display(Object o) {
        Integer num = (Integer)o;
        System.out.println(num);
    }
}
