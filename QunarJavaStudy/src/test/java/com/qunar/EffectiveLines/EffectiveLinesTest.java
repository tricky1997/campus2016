package com.qunar.EffectiveLines;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EffectiveLinesTest {
    public final static Logger log = LoggerFactory.getLogger(EffectiveLinesTest.class);
    private final static String path = System.getProperty("user.dir") + "/src/main/resources/StringUtils.java";
    private static int count;
    private static long startTime, endTime;

    @Test
    public void testCodeLineNumbers() throws Exception {
        startTime = System.currentTimeMillis();
        count = EffectiveLines.codeLineNumbers(path);
        endTime = System.currentTimeMillis();
        log.info("BufferedReader consume time:" + (endTime - startTime));
        log.info("valid code lines:" + count);
    }


    @Test
    public void testStatisticCodeLine() throws Exception {
        startTime = System.currentTimeMillis();
        count = EffectiveLines.statisticCodeLine(path);
        endTime = System.currentTimeMillis();
        log.info("scanner consume time:" + (endTime - startTime));
        log.info("valid code lines:" + count);
    }


    @Test
    public void testStatisticCodeLine1() throws Exception {
        startTime = System.currentTimeMillis();
        count = EffectiveLines.statisticCodeLine1(path);
        endTime = System.currentTimeMillis();
        log.info("Apache Commons IO consume time:" + (endTime - startTime));
        log.info("valid code lines:" + count);
    }


    @Test
    public void testStatisticCodeLine2() throws Exception {
        startTime = System.currentTimeMillis();
        count = EffectiveLines.statisticCodeLine2(path);
        endTime = System.currentTimeMillis();
        log.info("Guava IO consume time:" + (endTime - startTime));
        log.info("valid code lines:" + count);
    }

} 
