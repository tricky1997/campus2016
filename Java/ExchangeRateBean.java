package com.qunar.dan;

import java.util.ArrayList;

/**
 * Created by dan on 2016/6/22.
 */
public class ExchangeRateBean {
    private String link;// URL
    private String date;// 日期
    private static ArrayList<String> currencyName;// 美元 欧元 港元
    private ArrayList<Double> currencyValue;// 美元汇率 欧元汇率 港元汇率

    /* Empty Constructor */
    ExchangeRateBean(){

    }

    /* Getter and Setters */

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static ArrayList<String> getCurrencyName() {
        return ExchangeRateBean.currencyName;
    }

    public static void setCurrencyName(ArrayList<String> currencyName) {
        ExchangeRateBean.currencyName = currencyName;
    }

    public ArrayList<Double> getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(ArrayList<Double> currencyValue) {
        this.currencyValue = currencyValue;
    }
}
