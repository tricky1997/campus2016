package com.qunar.hjy;

/**
 * Created by lenovo on 2016/7/4.
 */

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jxl.*;
import jxl.write.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;


public class ExchangeRate {
    public static void main(String[] args) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String endDay = sdf.format(cal.getTime());
        cal.add(cal.DATE, -30);
        String startDay = sdf.format(cal.getTime());

        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action").data("projectBean.startDate", startDay).data("projectBean.endDate", endDay).get();
        } catch (IOException e) {
            e.printStackTrace();
        }



        WritableWorkbook wwb = null;
        File file = new File("ExchangeRate.xls");
        if(file.exists())
            file.delete();


        try {
            wwb = Workbook.createWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WritableSheet ws=null;
        ws = wwb.createSheet("30天内人民币汇率中间价", 0);   //创建sheet



        Element table = doc.getElementsByClass("list").first();
        Elements titles = table.getElementsByClass("table_head");
        Elements data = table.getElementsByClass("first");
        int[] Num = {0, 1, 2, 4};
        int j = 0;

        for (int i = 0; i < 4; i++) {
            j=0;


            String title = titles.get(Num[i]).text();
            Label lbl = new Label(i, j, title);
            try {
                ws.addCell(lbl);
            } catch (WriteException e) {
                e.printStackTrace();
            }
            j++;


            for(int k = 0;k<data.size();k++) {
                Elements exchangeRates = data.get(k).getElementsByTag("td");
                String str=exchangeRates.get(Num[i]).text();
                Label lbl1 = new Label(i, j,str);
                try {
                    ws.addCell(lbl1);
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                j++;
            }
        }

        try {
            wwb.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wwb.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}

