package com.qunar;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hanju on 2016/6/20.
 */
public class EffectiveLinesTest {
    private String filePath="test.java";

    @Test
    public void testEffectiveLines()
    {
        EffectiveLines effectiveLines=new EffectiveLines(filePath);
        System.out.println(effectiveLines.getEffectiveLine());;
    }
}