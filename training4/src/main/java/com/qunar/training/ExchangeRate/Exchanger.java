package com.qunar.training.ExchangeRate;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获得30天汇率信息的汇总工具
 * Created by shining.cui on 2016/6/6.
 */
public class Exchanger {
    PageAnalyzer pageAnalyzer = new PageAnalyzer();
    final String HKDCode = "1315";
    final String EUROCode = "1326";
    final String USDCode = "1316";

    public List<List<String>> getUSDCurrency( List<String> days) throws IOException {
       return getCurrency(days,USDCode);
    }

    public List<List<String>> getHKDCurrency( List<String> days) throws IOException {
        return getCurrency(days,HKDCode);
    }

    public List<List<String>> getEUROCurrency( List<String> days) throws IOException {
        return getCurrency(days,EUROCode);
    }

    /**
     * 根据传入的天数，以及货币代码获得相应的汇率信息
     * @param days 要查询的日期集合
     * @param currencyCode 查询货币对应的代码
     * @return 对应货币的对应日期集合的汇率信息集合
     * @throws IOException
     */
    private List<List<String>> getCurrency( List<String> days,String currencyCode) throws IOException {
        List<List<String>> currencyList = new ArrayList<List<String>>();
        for (String dayStr : days) {
            List<String> dayStrAndCurrencyList = new ArrayList<String>();
            BufferedReader responseContent = HttpPoster.getResponseContent(currencyCode, dayStr, dayStr);
            String currency = "";
            if (USDCode.equals(currencyCode)) {
                currency = pageAnalyzer.getUSDCurrency(responseContent);
            }else if (EUROCode.equals(currencyCode)) {
                currency = pageAnalyzer.getEUROCurrency(responseContent);
            }else if (HKDCode.equals(currencyCode)) {
                currency = pageAnalyzer.getHKDCurrency(responseContent);
            }
            dayStrAndCurrencyList.add(dayStr);
            dayStrAndCurrencyList.add(currency);
            currencyList.add(dayStrAndCurrencyList);
        }
        return currencyList;
    }

}
