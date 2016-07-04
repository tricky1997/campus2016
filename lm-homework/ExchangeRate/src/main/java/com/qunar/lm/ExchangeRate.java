package com.qunar.lm;

import jxl.*;
import jxl.write.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.*;
import java.text.*;
import java.util.*;
/**
 * 分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，得到人民币对美元、欧元、港币的汇率，形成excel文件输出。
 * 汇率数据找相关的数据源，自己爬数据分析。
 * <p>
 * 问题分析
 * 输入：java源文件目录
 * 输出：前十个被import最多的类
 * 思路：遍历文件方法+文件统计类方法+最终输出显示方法
 * 细节：递归遍历思路代码简单，性能受文件路径深度影响，
 * Created by lm704 on 2016/7/1 0001.
 */
public class ExchangeRate {
    public static void main(String[] args) {
        //定义开始与结束的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String endDay = sdf.format(cal.getTime());
        cal.add(cal.DATE, -30);
        String startDay = sdf.format(cal.getTime());
        //链接数据
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action").data("projectBean.startDate", startDay).data("projectBean.endDate", endDay).get();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //创建Excel
        WritableWorkbook wwb = null;
        File file = new File("ExchangeRate.xls");
        if(file.exists())   //如果存在，则删除
            file.delete();


        try {
            wwb = Workbook.createWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WritableSheet ws=null;
        ws = wwb.createSheet("近30天内人民币汇率中间价", 0);   //创建sheet


        //获取并写入Excel
        Element table = doc.getElementsByClass("list").first();
        Elements titles = table.getElementsByClass("table_head");
        Elements data = table.getElementsByClass("first");
        int[] Num = {0, 1, 2, 4};
        int j = 0;
        //向Excel写入数据
        for (int i = 0; i < 4; i++) {
            j=0;

            //写入表头
            String title = titles.get(Num[i]).text();
            Label lbl = new Label(i, j, title);
            try {
                ws.addCell(lbl);
            } catch (WriteException e) {
                e.printStackTrace();
            }
            j++;

            //写入每天的汇率中间价
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
