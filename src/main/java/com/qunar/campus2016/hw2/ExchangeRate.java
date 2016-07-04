package com.qunar.campus2016.hw2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import jxl.Workbook;
import jxl.write.*;

/**
 * Created by nicklaus on 2016/7/4.
 */
public class ExchangeRate {

    private static String webPage = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action";
    private static String excelFile = "src/main/java/com/qunar/campus2016/hw2/ExchangeRate.xls";

    private static int[] num = {0, 1, 2, 4};
    public static void main(String[] args) {

        //定义开始和结束时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String endDay = sdf.format(cal.getTime());
        cal.add(cal.DATE, -30);
        String startDay = sdf.format(cal.getTime());

        //链接数据
        Document doc = null;
        try {
            doc = Jsoup.connect(webPage).data("projectBean.startDate", startDay)
                    .data("projectBean.endDate", endDay).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建Excel
        WritableWorkbook wwb = null;
        File file = new File(excelFile);

        //如果存在，则删除
        if(file.exists())
            file.delete();

        try {
            wwb = Workbook.createWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        WritableSheet ws;
        //创建sheet
        ws = wwb.createSheet("近30天内人民币汇率中间价", 0);

        //获取并写入Excel
        Element table = doc.getElementsByClass("list").first();
        Elements titles = table.getElementsByClass("table_head");
        Elements data = table.getElementsByClass("first");

        int j = 0;
        //向Excel写入数据
        for (int i = 0; i < 4; i++) {
            j = 0;
            //写入表头
            String title = titles.get(num[i]).text();
            Label lbl = new Label(i, j, title);

            //添加单元
            try {
                ws.addCell(lbl);
            } catch (WriteException e) {
                e.printStackTrace();
            }
            j ++;

            //写入每天的汇率中间价
            for(int k = 0; k < data.size(); k++) {
                Elements exchangeRates = data.get(k).getElementsByTag("td");
                String str = exchangeRates.get(num[i]).text();
                lbl = new Label(i, j, str);

                //添加单元
                try {
                    ws.addCell(lbl);
                } catch (WriteException e) {
                    e.printStackTrace();
                }
               j ++;
            }

        }

        try {
            //写入excel
            wwb.write();
            //关闭excel
            wwb.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }

    }

}