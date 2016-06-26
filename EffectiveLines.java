/**
 * 第一题：统计一个Java文件的有效行数。
 1、有效不包括空行
 2、不考虑代码见有多行注释的情况
 */
package EffectiveLines;
/**
 * Created by liwen on 16-6-23.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EffectiveLines {

    static long normalLine = 0;
    static boolean comment = false;
    public static void main(String[] args) {
        File file = new File("E:\\EffectiveLines.java"); // 需要统计的文件夹路径
        getChild(file);
        System.out.println("文件为: " + file.getName());
        System.out.println("有效代码行数为: " + normalLine);

    }

    private static void getChild(File child) { // 遍历子目录
        if (child.getName().matches(".*\\.java$")) { // 只查询java文件
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(child));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            String line = "";
            try {
                while ((line = br.readLine()) != null) {
                    parse(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (child.listFiles() != null) {
            for (File f : child.listFiles()) {
                getChild(f);
            }
        }
    }

    private static void parse(String line) {
        line = line.trim();
        if (line.length() == 0) {
        }
        else if (comment) {
            if (line.endsWith("*/")) {
                comment = false;
            }
            else if (line.matches(".*\\*/.+")) {
                normalLine++;
                comment = false;
            }
        }
        else if (line.startsWith("//")) {
        }
        else if (line.matches(".+//.*")) {    //
            normalLine++;
        }
        else if (line.startsWith("/*") && line.matches(".+\\*/.+")) {
            normalLine++;
            if (findPair(line)) {
                comment = false;
            } else {
                comment = true;
            }
        }
        else if (line.startsWith("/*") && !line.endsWith("*/")) {
            comment = true;
        }
        else if (line.startsWith("/*") && line.endsWith("*/")) {
            comment = false;
        }
        else if (line.matches(".+/\\*.*") && !line.endsWith("*/")) {
            normalLine++;
            if (findPair(line)) {
                comment = false;
            }
            else {
                comment = true;
            }
        }
        else if (line.matches(".+/\\*.*") && line.endsWith("*/")) {
            normalLine++;
            comment = false;
        }
        else {
            normalLine++;
        }
    }

    private static boolean findPair(String line) { // 查找一行中/*与*/是否成对出现
        int count1 = 0;
        int count2 = 0;
        Pattern p = Pattern.compile("/\\*");
        Matcher m = p.matcher(line);
        while (m.find()) {
            count1++;
        }
        p = Pattern.compile("\\*/");
        m = p.matcher(line);
        while (m.find()) {
            count2++;
        }
        return (count1 == count2);
    }

}