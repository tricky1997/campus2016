package com.ExchangeRate;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 从网站下载30天的所有人民币汇率数据，存入webData.xls
 * 分析表格数据： 
 *   读取该表格文档，计算一个月内人民币汇率中间价，并输出至analysis.xls
 * 需要导入jxl.jar包
 */
public class ExchangeRate {

    private double dollar;  //美元汇率
    private double euro;  //欧元汇率
    private double hk_dollar;  //港元汇率

    public ExchangeRate() {
        this.dollar = 0;
        this.euro = 0;
        this.hk_dollar = 0;
    }

    /*
     *从网站下载30天的所有人民币汇率数据，存入webData.xls
     */
    public void getWebData() {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        URLConnection urlConnection = null;

        Calendar calendar = Calendar.getInstance();
        Date endDate = calendar.getTime();  //当前日期
        calendar.add(Calendar.DATE,-30);
        Date startDate = calendar.getTime();  //30天前日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  //日期格式
        String startDateStr = simpleDateFormat.format(startDate);
        String endDateStr = simpleDateFormat.format(endDate);

//        System.out.println(startDateStr);
//        System.out.println(endDateStr);

        //发送“中国外汇管理局”的导出表单请求
        String url = "http://www.safe.gov.cn/AppStructured/view/project_exportRMBExcel.action?projectBean.startDate="
            + startDateStr + "projectBean.endDate=" + endDateStr + "queryYN=true";
        System.out.println(url);
        try {
            urlConnection = new URL(url).openConnection();
            inputStream = urlConnection.getInputStream();
            fileOutputStream = new FileOutputStream("webData.xls");  //注意，因为周末没有数据，所以里面不是30行数据

            byte [] bytes = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1) {  //输出内容到本地表格上
                fileOutputStream.write(bytes,0,len);
                fileOutputStream.flush();
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 从webData.xls中读入数据，并分析计算汇率
     */
    public void readExcel() {
        Workbook workbook = null;
        try {
            InputStream inputStream = new FileInputStream("webData.xls");
            workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(0);  //找到文件第一张表格
            int rows = sheet.getRows();  //获取总行数

            //计算美元、欧元、港元汇率
            for (int i = 1; i < rows; ++i) {
                Cell dollar_cell = sheet.getCell(1,i);
                dollar += Double.parseDouble(dollar_cell.getContents());

                Cell euro_cell = sheet.getCell(2,i);
                euro += Double.parseDouble(euro_cell.getContents());

                Cell hk_dollar_cell = sheet.getCell(4,i);
                hk_dollar += Double.parseDouble(hk_dollar_cell.getContents());
            }
            dollar = dollar/rows;
            euro = euro/rows;
            hk_dollar = hk_dollar/rows;

            inputStream.close();
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 创建analysis.xls文件，并将分析结果输出到文件中
     */
    public void writeExcel() {
        try {
            OutputStream os = new FileOutputStream(new File("analysis.xls"));
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(os);
            WritableSheet writableSheet = writableWorkbook.createSheet("人民币汇率分析",0);  //创建表格文件中第一页表格

            //输出表格第一行内容
            Label jieshao = new Label(0,0,"人民币汇率中间价");
            writableSheet.addCell(jieshao);
            Label meiyuan = new Label(1,0,"美元");
            writableSheet.addCell(meiyuan);
            Label ouyuan = new Label(2,0,"欧元");
            writableSheet.addCell(ouyuan);
            Label gangyuan = new Label(3,0,"港元");
            writableSheet.addCell(gangyuan);

            //输出计算结果到表格中
            Label l_dollar = new Label(1,1,""+dollar);
            writableSheet.addCell(l_dollar);
            Label l_euro = new Label(2,1,""+euro);
            writableSheet.addCell(l_euro);
            Label l_hk = new Label(3,1,""+hk_dollar);
            writableSheet.addCell(l_hk);

            writableWorkbook.write();
            writableWorkbook.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExchangeRate er = new ExchangeRate();
        er.getWebData();
        er.readExcel();
        er.writeExcel();
    }
}
