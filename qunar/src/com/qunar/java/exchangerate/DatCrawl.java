package com.qunar.java.exchangerate;

import org.jsoup.Jsoup;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: 刘能
 * Date: 16-6-28
 * Time: 下午1:34
 * 功能：数据获取类
 */
public class DatCrawl {

    /**
     * 功能：使用网络爬虫爬取数据
     *
     * @param urlStr:爬取数据的网址
     * @param startDate：开始日期
     * @param endDate：结束日期
     * @return
     */
    public Document crawlHtmlPageData(String urlStr, String startDate, String endDate) {
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(urlStr + "?projectBean.startDate=" + startDate + "&projectBean.endDate=" + endDate + "&queryYN=true").openStream(), "utf-8", urlStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
