package com.EffectiveLines;

import java.io.*;

/**
 * 统计有效文件行数，不考虑多行注释
 */
public class EffectiveLines {
    private int couners;  //统计有效行数的计数器

    public EffectiveLines() {
        this.couners = 0;
    }

    /*
    读入文件，统计有效行数
    filename为文件名
    返回有效行数
     */
    public int readFile(String filename) {
        File file = new File(filename);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                int cursor = 0; //游标，遍历一行数据用
                line = line.replace(" ","");  //去掉所有空格
                if(line.length() == 0) {  //全是空格的，不是有效行
                    continue;
                }
                //去掉空格后，剩下的是注释的话，也不是有效行
                if(line.charAt(0)=='/' && (line.charAt(1)=='/' || line.charAt(1)=='*')) {
                    continue;
                }
                couners++;
            }
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  this.couners;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("this program needs 1 arguments");
            System.exit(0);
        }
        EffectiveLines effectiveLines = new EffectiveLines();
        int answer = effectiveLines.readFile(args[0]);
        System.out.println(answer);
    }
}
