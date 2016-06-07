package com.qunar.training.ExchangeRate;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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

    private List<List<String>> getCurrency( List<String> days,String currencyCode) throws IOException {
        List<List<String>> USDCurrency = new ArrayList<List<String>>();
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
            USDCurrency.add(dayStrAndCurrencyList);
        }
        return USDCurrency;
    }

}
