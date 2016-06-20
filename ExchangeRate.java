package com.ys.qunar.home_work;


import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by shuang on 2016/6/14.
 * 分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，得到人民币对美元、欧元、港币的汇率，形成excel文件输出。
 */
public class ExchangeRate {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static List<List> lists;//保存表单数据
    public static int[] Index = {0,1,2,4};//0:time 1:usd 2:eur 4:hkd

    public static void writeToExcel(){
        System.out.println("-----将数据写入excel中------");
       // String[] title = {"日期","美元","欧元","港币"};
        String classPath = ExchangeRate.class.getClass().getResource("/").getPath();
        File file = new File(classPath+"//ExchangeRate.xls");
        List<List> lists = getData();
        //创建工作簿
        WritableWorkbook wwb;
        try {
            wwb = Workbook.createWorkbook(file);
            //添加第一个工作表
            WritableSheet sheet = wwb.createSheet("三十日人民币汇率",0);
            Label label;
            //开始写入内容
            int y;//标记列位置
            for (int x = 0; x < 31; x++) {
                List<String> list = (List<String>) lists.get(x);
                y = 0;
                for(String str:list){
                    // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
                    // 在Label对象的子对象中指明单元格的位置和内容
                    label = new Label(y,x,str);
                    sheet.addCell(label);
                    y++;
                }
            }
            wwb.write();
            wwb.close();
        } catch (Exception e){
            System.out.println("----数据写入错误-----");
            e.printStackTrace();
        }
        System.out.println("----数据写入完毕-----");
    }

    public static List getData(){
        System.out.println("-----读取人民币中间价汇率！--------");
        Calendar calendar = Calendar.getInstance();
        Date endDate = calendar.getTime();//查询终止时间
        String endDateStr = sdf.format(endDate);
        //得到人民币汇率中间价的三十天记录,设置40天时未达到30条记录
        calendar.add(Calendar.DAY_OF_MONTH,-50);
        Date startDate = calendar.getTime();//查询起始时间
        String startDateStr = sdf.format(startDate);

        Connection conn = Jsoup.connect("http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action?projectBean.startDate="+
                startDateStr+"&projectBean.endDate="+endDateStr);
        Document doc = null;
        try {
             doc = conn.timeout(1000000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element result = doc.getElementsByClass("list").get(0);
        lists = new ArrayList<>();
        Elements titles = result.getElementsByClass("table_head");//标题
        Elements rows = result.getElementsByClass("first");//数据
        List<String> titleList = new ArrayList<>();//保存标题行
        for(int i:Index){
            String title = titles.get(i).text();
            titleList.add(title);
        }
        lists.add(titleList);
        for(Element row:rows){
            Elements columns = row.getElementsByTag("td");
            List<String> list = new ArrayList<>();//保存每行数据
            for(int i:Index){
                String text = columns.get(i).text();
                list.add(text);
            }
            lists.add(list);
        }
        System.out.println("------数据读取并分析完毕-------");
        return lists;

    }
    public static void main(String[] args)  {
        writeToExcel();
    }
}
