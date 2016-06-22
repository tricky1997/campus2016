package com.qunar;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hanju on 2016/6/20.
 */
public class CountMostImportTest {
    @Test
    public void getTopTen() throws Exception {
        CountMostImport countMostImport=new CountMostImport();
        for(String string:countMostImport.getTopTen())
        {
            System.out.println(string);
        }
    }

}