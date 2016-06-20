package com.qunar.homework.Units.Work;

import com.qunar.homework.Units.Interface.WorkHandler;

/**
 * Created by Administrator on 2016/6/20.
 */
public class NothingWork implements WorkHandler{
    public boolean getWorkDone(String line) {
        return true;
    }

    public Object getData() {
        return null;
    }
}
