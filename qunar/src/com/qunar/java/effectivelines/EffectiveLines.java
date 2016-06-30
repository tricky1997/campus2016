package com.qunar.java.effectivelines;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: 刘能
 * Date: 16-6-27
 * Time: 下午12:49
 * 功能：判断Java文件的有效代码行数
 */
public class EffectiveLines {

    //是否有多行注释标志
    private boolean isMultiLineComment = false;

    /**
     * @param fileName : 文件名字
     * @return ：返回有效行数
     *         功能：统计java代码中的有效行数
     */
    public int statisticsFileLines(String fileName) {
        File file = new File(fileName);
        InputStreamReader reader = null;
        BufferedReader br = null;
        int count = 0;
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            br = new BufferedReader(reader);
            String line = br.readLine();
            while (line != null) {
                line = line.trim();
                if (isEffectiveLine(line)) {//判断是否为有效行
                    count++;
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        return count;
    }

    /**
     * @param lineStr:需要判断的字符串
     * @return 功能：判断字符串中是否包含多行注释的起始标志
     */
    public boolean isStartMultiLineComments(String lineStr) {
        return lineStr.contains("/*") && lineStr.indexOf("/*") == 0;
    }

    /**
     * @param lineStr:需要判断的字符串
     * @return g功能：判断字符串中是否包含多行注释的结束标志
     */
    public boolean isEndMultiLineComments(String lineStr) {
        return lineStr.contains("*/") && lineStr.indexOf("*/") == (lineStr.length() - 2);
    }

    /**
     * @param lineStr:需要判断的字符串
     * @return 功能：判断该行中的字符串是否为有效行
     */
    public boolean isEffectiveLine(String lineStr) {
        if (!isMultiLineComment && isStartMultiLineComments(lineStr)) {
            isMultiLineComment = true;
            return false;
        } else if (isMultiLineComment && !isEndMultiLineComments(lineStr)) {
            return false;
        } else if (isMultiLineComment && isEndMultiLineComments(lineStr)) {
            isMultiLineComment = false;
            return false;
        }
        if (lineStr.trim().length() == 0 || (lineStr.contains("//") && lineStr.indexOf("/") == 0)) {
            return false;
        }
        return true;
    }
}
