package com.qunar.training.ExchangeRate;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * 分析页面，获得页面中的汇率信息
 * Created by shining.cui on 2016/6/6.
 */
public class PageAnalyzer {
    DecimalFormat format = new DecimalFormat("0.00");
    final String USDStr = "<td>美元</td>";
    final String HKDStr = "<td>港币</td>";
    final String EUROStr = "<td>欧元</td>";

    public  String getUSDCurrency(BufferedReader pageContentReader) throws IOException {
       return getCurrency(pageContentReader,USDStr);
    }

    public  String getHKDCurrency(BufferedReader pageContentReader) throws IOException {
        return getCurrency(pageContentReader,HKDStr);
    }

    public  String getEUROCurrency(BufferedReader pageContentReader) throws IOException {
        return getCurrency(pageContentReader,EUROStr);
    }

    /**
     * 根据网页页面内容获得外汇信息
     * @param pageContentReader 网页页面的BufferedReader
     * @param currencyStr 对应货币的代码
     * @return 当天该货币的汇率信息
     * @throws IOException
     */
    private  String getCurrency(BufferedReader pageContentReader,String currencyStr) throws IOException {
        String currency = "";
        String line;
        double sumCurrency = 0.0;
        int currencyNumber = 0;
        while ((line = pageContentReader.readLine()) != null) {
            String currencyLine = "";
            if (line.trim().contains(currencyStr)) {
                //找到中行折算价对应栏
                for (int i = 0; i < 9; i++) {
                    pageContentReader.readLine();
                }
                currencyLine = pageContentReader.readLine();
                String trim;
                if (currencyLine != null && (trim = currencyLine.trim()) != "") {
                    currencyNumber++;
                    int endIndex = trim.indexOf("</");
                    currency = trim.substring(4, endIndex);
                    sumCurrency += Double.parseDouble(currency);
                }
            }
        }
        String formatedCurrency = format.format(sumCurrency / currencyNumber);
        return formatedCurrency;
    }
}
