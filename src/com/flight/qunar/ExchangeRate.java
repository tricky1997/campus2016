package com.flight.qunar;

import jxl.Workbook;
import jxl.write.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 问题：分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，得到人民币对美元、欧元、港币的汇率，形成excel文件输出。
 *
 * 数据：来源为“国家外汇管理局” http://www.safe.gov.cn/
 *
 * 方法1. 简单暴力的方法是直接“导出表格”，但是会将table中所有的汇率数据都保存下来； 见方法getExchangeRate()
 * 方法2. 先爬数据再保存为Excel文件，只保存要求的3种汇率； 见方法getExchangeRateQuery()
 *
 * Created by 冯麒 on 2016/6/15.
 */
public class ExchangeRate {

    //爬数据并保存
    public static final String EXCHANGERATE_QUERY_URL = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action?";
    //直接导出表格
    public static final String EXCHANGERATE_EXPORT_URL = "http://www.safe.gov.cn/AppStructured/view/project_exportRMBExcel.action?";

    public static final String FILE_Excel = "C:\\Users\\Administrator\\Desktop\\RMBExchangeRateExcel.xls";
    public static final String FILE_Query = "C:\\Users\\Administrator\\Desktop\\RMBExchangeRateQuery.xls";

    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String end = simpleDateFormat.format(date);
        //间隔30天
        date = new Date(date.getTime()-29*24*60*60*1000L);
        String start = simpleDateFormat.format(date);
        //URL参数
        String parameter = "projectBean.startDate=" + start + "&projectBean.endDate=" + end;

        //简单暴力的方法
        getExchangeRateExcel(parameter);
        //复杂细致的方法
        //getExchangeRateQuery(parameter);
    }

    public static void getExchangeRateExcel(String parameter) {
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            URL url = new URL(EXCHANGERATE_EXPORT_URL + parameter);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.connect();
            InputStream inputStream = httpUrlConnection.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream);

            //存储在桌面文件中
            File file = new File(FILE_Excel);
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            int n;
            byte[] data = new byte[4096];
            while ((n = bufferedInputStream.read(data)) != -1) {
                bufferedOutputStream.write(data, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if(bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } catch (Exception e) {
            }

        }
    }

    public static void getExchangeRateQuery(String parameter) {
        OutputStream outputStream = null;
        WritableWorkbook writableWorkbook = null;
        try {
            //创建Excel文件
            File file = new File(FILE_Query);
            if(file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            writableWorkbook = Workbook.createWorkbook(outputStream);
            WritableSheet writableSheet = writableWorkbook.createSheet("人民币汇率", 0);
            WritableFont writableFont = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD, true);
            WritableCellFormat writableCellFormat = new WritableCellFormat(writableFont);
            writableCellFormat.setAlignment(jxl.format.Alignment.CENTRE);
            writableSheet.setColumnView(0, 20);
            writableSheet.addCell(new Label(0, 0, "日期", writableCellFormat));
            writableSheet.addCell(new Label(1, 0, "美元", writableCellFormat));
            writableSheet.addCell(new Label(2, 0, "欧元", writableCellFormat));
            writableSheet.addCell(new Label(3, 0, "港币", writableCellFormat));

            Document document = Jsoup.connect(EXCHANGERATE_QUERY_URL + parameter).get();
            Element element = document.getElementById("InfoTable");
            Elements trData = element.getElementsByTag("tr");

            for(int i=1; i<trData.size(); i++) {
                Elements tdData = trData.get(i).getElementsByTag("td");
                writableSheet.addCell(new Label(0, i, tdData.get(0).text(), writableCellFormat));
                writableSheet.addCell(new Label(1, i, tdData.get(1).text(), writableCellFormat));
                writableSheet.addCell(new Label(2, i, tdData.get(2).text(), writableCellFormat));
                writableSheet.addCell(new Label(3, i, tdData.get(4).text(), writableCellFormat));
            }

            writableWorkbook.write();
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(writableWorkbook != null) {
                    writableWorkbook.close();
                }
                if(outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
