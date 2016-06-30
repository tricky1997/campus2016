package com.qunar.java.exchangerate;

import org.jsoup.nodes.Document;

import java.util.*;

import org.jsoup.select.Elements;

/**
 * Created by IntelliJ IDEA.
 * User: 刘能
 * Date: 16-6-27
 * Time: 下午6:59
 * 功能：计算汇率类
 */
public class ExchangeRate {

    /**
     * 功能：根据爬取的数据计算汇率
     *
     * @param urlStr：爬取数据的网址
     * @return
     */
    public HashMap<String, ArrayList<String>> getExchangeRate(String urlStr) {
        Date endDate = new Date();
        Date startDate = DateTimeUtil.getDateBefore(endDate, 30);
        Document doc = new DatCrawl().crawlHtmlPageData(urlStr, DateTimeUtil.formatDate(startDate), DateTimeUtil.formatDate(endDate));
        Elements trs = doc.getElementById("InfoTable").getElementsByTag("tr");
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for (int i = 1; i < trs.size(); i++) {
            Elements tds = trs.get(i).getElementsByTag("td");
            String[] strsOfDollar = tds.get(1).html().trim().split("&");
            String[] strsOfEUR = tds.get(2).html().trim().split("&");
            String[] strsOfHK = tds.get(4).html().trim().split("&");
            String[] strsOfDate = tds.get(0).html().trim().split("&");
            ArrayList<String> list = new ArrayList<String>();
            list.add(strsOfDollar[0]);
            list.add(strsOfEUR[0]);
            list.add(strsOfHK[0]);
            map.put(strsOfDate[0], list);
        }
        return map;
    }

}
