package com.qunar.homework.EffectiveLines;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 源代码文件有效代码行统计工具
 * Created by zhaocai-luo on 2016/6/15.
 */
public class EffectiveLinesCounter {
    /**
     * 分析每一行，统计有效行数
     *
     * @param bufferedReader 源代码文件的BufferedReader
     * @return 有效行数
     */
    public int getEffectiveLines(BufferedReader bufferedReader){
        String eachLine;
        int effectiveLines = 0;

        // 多行注释标志
        boolean multiLineComment = false;
        try {
            while ((eachLine = bufferedReader.readLine()) != null){
                // 去掉每一行首尾的无效字符，包括空格、制表符等
                String trimLine = eachLine.trim();

                if (multiLineComment)
                {
                    if (trimLine.endsWith("*/")){   // 结束多行注释
                        multiLineComment = false;
                    }
                }
                else if (trimLine.startsWith("/*")){
                    if (trimLine.endsWith("*/")){   // 只占一行的多行注释
                        continue;
                    }
                    else {
                        multiLineComment = true;    // 进入多行注释模式
                    }
                }
                else if ((trimLine.length() > 0) && (!trimLine.startsWith("//"))){
                    effectiveLines += 1;            // 非空行非注释时，判断为有效行
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return effectiveLines;
    }
}
