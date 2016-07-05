package com.qunar.exchangerate;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Charmy on 2016/6/30.
 * 使用Jsoup抓取网络数据
 */
public class Crawler {
    /**
     * @param url 获取中间价数据的网址
     * @return 结果存在链表中
     */
    public ArrayList<ResultData> getMiddleRate(String url) {
        ArrayList<ResultData> list = new ArrayList<ResultData>();
        Connection conn = Jsoup.connect(url);
        //查询日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前日期
        Calendar endDate = Calendar.getInstance();
        //前30天的日期
        Calendar startDate = Calendar.getInstance();
        startDate.add(startDate.DATE, -30);
        //转换成字符串格式
        String startTime = sdf.format(startDate.getTime());
        String endTime = sdf.format(endDate.getTime());

        //爬取30天的中间价数据
        conn.data("projectBean.startDate", startTime);
        conn.data("projectBean.endDate", endTime);

        Document doc = null;
        try {
            doc = conn.get();
            //解析html文件，数据在<tr 的class="frist"中
            Elements elements = doc.getElementsByClass("first");
            for (Element elem : elements) {
                ResultData rd = new ResultData();
                rd.setDate(elem.child(0).text());
                rd.setDollar(elem.child(1).text());
                rd.setEuro(elem.child(2).text());
                rd.setHkd(elem.child(4).text());
                list.add(rd);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
