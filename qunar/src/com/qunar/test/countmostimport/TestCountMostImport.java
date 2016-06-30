package com.qunar.test.countmostimport;

import com.qunar.java.countmostimport.CountMostImport;
import com.qunar.java.effectivelines.EffectiveLines;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 16-6-27
 * Time: 下午4:25
 * 功能：import最多类的测试类
 */
public class TestCountMostImport {

    @Test
    public void testCountMostImport(){
        String  directorStr = "D:\\commonsoftware\\IntelliJIDEA\\IntelliJ IDEA 10.0.2\\qunar\\src\\com\\qunar\\java\\exchangerate";
        CountMostImport cmi = new CountMostImport(directorStr);
        cmi.fileTraversal();
        ArrayList<Map.Entry<String, Integer>> list = cmi.sortHashMap(cmi.getClassTimes());
        cmi.printTop10Class(list);
    }
}
