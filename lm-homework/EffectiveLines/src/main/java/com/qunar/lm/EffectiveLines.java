package com.qunar.lm;

import java.io.*;
import java.util.*;

/**
 * 问题分析
 * 输入：java文件路径
 * 输出：有效行数
 * 思路：遍历文件方法+文件统计类方法+最终输出显示方法
 * 细节：空行
 * 不含多行注释
 * 考虑单行注释
 * Created by lm704 on 2016/7/1 0001.
 */
public class EffectiveLines {
    private static int codeLines;

    public void computeLines(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.trim().equals(""))
                    continue;
                else if (line.trim().startsWith("//"))
                    continue;
                codeLines++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("未找到指定的文件");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件流
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        EffectiveLines el = new EffectiveLines();
        Scanner sca = new Scanner(System.in);
        System.out.println("请输入文件的绝对路径！");
        while (sca.hasNext()) {
            File file = new File(sca.nextLine());
            el.computeLines(file);
            System.out.println("有效行数： " + codeLines);
            codeLines = 0;
            System.out.println("请输入文件的绝对路径！");
        }
    }
}
