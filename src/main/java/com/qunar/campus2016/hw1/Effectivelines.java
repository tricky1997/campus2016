package com.qunar.campus2016.hw1;

import java.io.*;

/**
 * Created by nicklaus on 7/4/16.
 */
public class Effectivelines {

    private static int totalLines = 0;
    private static String filePath = "src/main/java/com/qunar/campus2016/hw1/Test.java";

    public static void main(String[] args) {

        File file = new File(filePath);
        compute(file);
        System.out.println("EffectiveLines is " + totalLines);
    }

    //计算有效行数核心函数
    public static void compute(File file) {

        BufferedReader bf = null;
        try {
            bf = new BufferedReader(
                    new FileReader(file));
            String curLine;
            while((curLine = bf.readLine()) != null) {

                if(curLine.trim().equals("")) {
                    continue;
                } else if (curLine.trim().startsWith("//") || curLine.trim().startsWith("/*")
                            || curLine.trim().startsWith("*/") || curLine.trim().startsWith("*")) {
                    continue;
                }
                totalLines ++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
