package com.qunar.homework.ExchangeRate;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 网页源代码分析器，用于提取网页源代码中的外汇信息
 * Created by zhaocai-luo on 2016/6/15.
 */
public class PageParser {
    /**
     * 网页响应结果分析，提取外汇信息
     * @param page 输入的网页响应信息
     * @return 历史时期对应的外汇汇率中间值列表
     */
    public List<String> parsePage(String page){
        List<String> currencyList = new ArrayList<String>();

        InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(page.getBytes()));
        BufferedReader br = new BufferedReader(in);
        String line;
        try {
            while ((line = br.readLine()) != null){
                // 匹配诸如'××××年××月××日'的行
                if (line.matches("^.*<.+>\\d{4}年\\d{2}月\\d{2}日<.+>$")){
                    for (int i = 0; i < 5; ++i){    // 分析源代码网页可知第五行为货币的中间值数据
                        line = br.readLine();
                    }
                    String trimLine = line.trim();
                    // 在诸如<td class="numberright">84.52</td>的格式中提取汇率中间值
                    String currency = trimLine.substring(trimLine.indexOf('>') + 1,trimLine.lastIndexOf('<'));

                    currencyList.add(currency);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencyList;
    }

}
