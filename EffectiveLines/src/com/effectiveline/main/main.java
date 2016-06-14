package com.effectiveline.main;

import com.effectiveline.service.IEffectiveLineService;
import com.effectiveline.service.impl.EffectiveLineServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/6/4.
 */
public class main {

    public static void main(String[] args) {

        String relativelyPath = System.getProperty("user.dir");
        String path = relativelyPath + "\\src\\test\\com\\effectiveline\\service\\EffectiveLineServiceImplTest.java";

        IEffectiveLineService ie = new EffectiveLineServiceImpl();
        System.out.println(ie.countEffectiveLine(path));

    }

}
