package com.qunar.EffectiveLines;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * test the Effective class that different method's performance
 */
public class TestResult {
    public static Logger log = LoggerFactory.getLogger(TestResult.class);

    public static void main(String[] args) {
        int count = 0;
        long startTime, endTime;
        String path = System.getProperty("user.dir") + "/QunarJavaStudy/src/main/resources/StringUtils.java";
        log.debug("path:" + path);
        try {
            startTime = System.currentTimeMillis();
            count = EffectiveLines.codeLineNumbers(path);
            endTime = System.currentTimeMillis();
            log.info("BufferedReader consume time:" + (endTime - startTime));
            log.info("valid code lines:" + count);

            //扩展测试
            startTime = System.currentTimeMillis();
            count = EffectiveLines.statisticCodeLine(path);
            endTime = System.currentTimeMillis();
            log.info("scanner consume time:" + (endTime - startTime));
            log.info("valid code lines:" + count);

            startTime = System.currentTimeMillis();
            count = EffectiveLines.statisticCodeLine1(path);
            endTime = System.currentTimeMillis();
            log.info("Apache Commons IO consume time:" + (endTime - startTime));
            log.info("valid code lines:" + count);

            startTime = System.currentTimeMillis();
            count = EffectiveLines.statisticCodeLine2(path);
            endTime = System.currentTimeMillis();
            log.info("Guava IO consume time:" + (endTime - startTime));
            log.info("valid code lines:" + count);

        } catch (IllegalArgumentException e) {
            log.warn(e.toString());
        } catch (IOException e) {
            log.warn(e.toString());
        }
    }
}
