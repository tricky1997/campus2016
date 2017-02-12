package com.qunar.homework.EffectiveLines;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * 文件处理相关工具，这里主要用于读取源程序文件
 * Created by zhaocai-luo on 2016/6/15.
 */
public class FileUtil {

    /**
     * 获取指定文件的BufferReader
     * @param file 输入文件对象
     * @return 输入文件对应的BufferedReader
     */
    public BufferedReader getBufferedReaderFromFile(File file){
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        return bufferedReader;
    }
}
