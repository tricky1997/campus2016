package com.qunar.training.EffectiveLines;

import java.io.*;

/**
 * 文件处理工具
 * Created by shining.cui on 2016/6/5.
 */
public class FileLineCounter {

    /**
     * 根据文件获取对应BufferedReader
     *
     * @param file 输入文件对象
     * @return 该对象对应的Reader
     */
    public BufferedReader getBufferedReader(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        return bufferedReader;
    }

    /**
     * 根据BufferedReader,遍历每行，统计有效行数
     *
     * @param bufferedReader 文件对应的BufferedReader
     * @return 有效行数
     */
    public int getEffectiveLines(BufferedReader bufferedReader) {
        String readLine;

        int effectiveLines = 0;
        try {
            while ((readLine = bufferedReader.readLine()) != null) {
                String trimLine = readLine.trim();
                //若每行以"//"则为注释，非有效行。trim后长度为0则证明为空白行，非有效行。
                if (!trimLine.startsWith("//") && trimLine.length() != 0) {
                    effectiveLines++;
                }
            }
        } catch (IOException e) {
            System.out.println("文件读取错误");
        }
        return effectiveLines;
    }
}
