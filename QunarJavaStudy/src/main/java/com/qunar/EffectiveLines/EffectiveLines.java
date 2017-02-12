package com.qunar.EffectiveLines;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * 统计一个java文件有效行数，不考虑代码间多行注释情况
 */
public class EffectiveLines {
    public final static Logger log = LoggerFactory.getLogger(EffectiveLines.class);
    //标记代码是否是代码块
    private static boolean isBlock = false;

    /**
     * 一、使用BufferedReader读取
     * 其中，不能用FileReader作为Reader，有平台兼容性
     * @param path  文件路径
     * @return  有效代码行
     * @throws IOException
     */
    public static int codeLineNumbers(String path) throws IOException {
        int count = 0;
        FileInputStream fStream = null;
        InputStreamReader reader = null;
        BufferedReader in = null;
        try {
            fStream = new FileInputStream(path);
            reader = new InputStreamReader(fStream, "UTF-8");//将InputSteam转换成Reader
            in = new BufferedReader(reader);    //只提供缓冲区的包装方法
            count = countCodeLine(in);
        } catch (FileNotFoundException e) {
            log.info("file not found :" + e.getMessage());
            throw e;
        } catch (SecurityException e) {
            log.info("something maybe security :" + e.getMessage());
            throw e;
        } catch (UnsupportedEncodingException e) {
            log.info("encoding wrong :" + e.getMessage());
            throw e;
        } catch (IOException e) {
            log.info("check please:" + e.getMessage());
            throw e;
        }finally {
            try{ if (fStream != null) fStream.close();}catch (IOException e){log.warn("FileInputStream close failed");}
            try{ if (reader != null) reader.close();}catch (IOException e){log.warn("InputStreamReader close failed");}
            try{ if (in != null) in.close();}catch (IOException e){log.warn("BufferedReader close failed");}
        }
        return count;
    }

    //从buffer中一行一行地获取数据，并统计
    private static int countCodeLine(BufferedReader buffin) throws IOException {
        String buffer;
        String strLine;
        isBlock = false;    //重置块标志
        int count = 0;
        while ((buffer = buffin.readLine()) != null) {
            strLine = buffer.trim();
            count += isCodeLine(strLine) ? 1 : 0;
        }
        return count;
    }

    /**
     * 考虑某行是否是代码行
     * 思路1：排除
     * 1. //注释
     * 2. 单行块注释
     * 3. 块注释
     * 4. { // 或 {/*
     * 5. } 或 }//
     * 6. length<3 一行的长度小于3肯定不是有效行
     * 7. 空行(空格也算长度)
     * 思路2：换个思路（这里只考虑标准代码书写的情况）
     */
    //思路1解法
    private static boolean isCodeLine(String strLine) {
        if (strLine.endsWith("*/")) {
            isBlock = false;
        }
        if (strLine.startsWith("/*")) {
            if (strLine.endsWith("*/")) { //单行块注释
                return false;
            }
            isBlock = true; //多行快注释
            return false;
        }
        if (isBlock || strLine.length() < 3 || strLine.startsWith("}") || strLine.startsWith("//")) {
            return false;
        }
        if (strLine.startsWith("{")) { //排除{//----
            String tmp = strLine.substring(1).trim();
            if (tmp.startsWith("//") || (tmp.startsWith("/*") && tmp.endsWith("*/"))) {
                return false;
            }
            //tmp.trim().startsWith("/*") && !tmp.trim().endsWith("*/")//不考虑代码间的多行注释情况
            return true;
        }
        return true;
    }

    //思路2解法
    private static boolean isCodeLine2(String line) {
        if (isBlock == true) {
            if (line.endsWith("*/")) {
                isBlock = false;
            }
            return false;
        } else {
            if (line.startsWith("/*")) {
                isBlock = true;
                return false;
            }
            if (line.length() < 3 || line.startsWith("//") || line.startsWith("}")) {
                return false;
            }
            return true;
        }
    }

    //扩展：使用其他的文件读取方法
    //-----使用扫描器读取，scanner---------------------------------------------------------------
    public static int statisticCodeLine(String path) throws IOException {
        Scanner scanner = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            scanner = new Scanner(inputStream, "UTF-8");
            int count = countCodeLine(scanner);
            if (scanner.ioException() != null) {
                throw scanner.ioException();
            }
            return count;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static int countCodeLine(Scanner scanner) throws IOException {
        int count = 0;
        isBlock = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            count += isCodeLine(line) ? 1 : 0;
        }
        return count;
    }

    //----Apache Commons IO----------------------------------------------------------
    /**
     * Apache Commons IO流(行迭代器)
     * 文件不大，也可以直接使用FileUtils.readLines()来做
     */
    public static int statisticCodeLine1(String path) throws IOException {
        LineIterator it = FileUtils.lineIterator(new File(path), "UTF-8");
        int count = 0;
        try {
            while (it.hasNext()) {
                String line = it.nextLine().trim();
                count += isCodeLine(line) ? 1 : 0;
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
        return count;
    }

    //-----Guava-------------------------------------------------------------------------------------
    /**
     * 一次性读取整个文件的所有行
     * 如果一行一行的读，需要实现它的LineProcessor<Iteger>接口
     */
    public static int statisticCodeLine2(String path) throws IOException {
        int count = 0;
        isBlock = false;
        List<String> lines = Files.readLines(new File(path), Charsets.UTF_8);
        for (String line : lines) {
            //log.info(line);
            boolean b = isCodeLine(line.trim());
            //log.info("this line is:" + b);
            count += b ? 1 : 0;
        }
        return count;
    }

}
