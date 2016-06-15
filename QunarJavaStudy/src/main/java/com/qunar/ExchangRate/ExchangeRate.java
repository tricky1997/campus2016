package com.qunar.ExchangRate;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 分析从今天开始过去30天时间里，人民币汇率中间价，美元、欧元、港币，excel输出
 */
public class ExchangeRate {
    public static final Logger log = LoggerFactory.getLogger(ExchangeRate.class);
    public static final String url = "http://www.chinamoney.com.cn/fe-c/historyParity.do?";
    public static final int timesLength = 30;

    /**
     * 主方法
     */
    public static void getExchangeRate() {
        //获取指定时间戳的请求字符串
        String urlString = urlConnect();
        log.debug(urlString);
        //得到请求的内容（html格式）
        String htmlContent = getHtmlDate(urlString);
        //解析，存到list中
        List<List<String>> exchangeRate = parseHtmlContent(htmlContent);
        //提取list中美元、欧元、港币的数据，直接删除exchangeRate数据
        List<List<String>> threeRateList = dealListDate(exchangeRate);
        exchangeRate =null;
        //输出到excel中
        String path = System.getProperty("user.dir") + "/QunarJavaStudy/src/main/resources/ExchangeRate.xls";
        boolean isSuccess = printToExcel(threeRateList, path);
        if (!isSuccess) {
            log.info("fail to writ the content to the excel file.");
        }
    }

    //将请求字符串连接起来
    private static String urlConnect() {
        String startDate = getTimestamp(timesLength);
        String endDate = getTimestamp(0);
        StringBuilder urlString = new StringBuilder(90);
        urlString.append(url);
        urlString.append("startDate=" + startDate);
        urlString.append("&endDate=" + endDate);
        return urlString.toString();
    }

    /**
     * 获取基于当前时间的某个时间
     *
     * @param daysNum   当前时间-天数=过去的某天
     */
    protected static String getTimestamp(int daysNum) {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        c.add(Calendar.DATE, -daysNum);
        date = c.getTime();
        String dateString = sdf.format(date);
        return dateString;
    }

    /**
     * 读取对应URL的全部内容，以字符串返回
     *
     * @param urlString 要获取的URL地址
     * @return
     */
    protected static String getHtmlDate(String urlString) {
        URL url = null;
        //预设2w个字符
        StringBuilder html = new StringBuilder(20000);
        try {
            url = new URL(urlString);
            // 内容是文本，直接以缓冲字符流读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String data = null;
            while ((data = reader.readLine()) != null) {
                html.append(data);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html.toString();
    }

    //解析特定的网页数据
    private static List<List<String>> parseHtmlContent(String html) {
        List<List<String>> rate = new ArrayList<List<String>>(timesLength);
        Document doc = Jsoup.parse(html);

        Elements titles = doc.select("td.dreport-title-1,td.dreport-title");
        Elements dates = doc.select("td.dreport-row1-1,td.dreport-row2-1");
        Elements contents = doc.select("td.dreport-row1,td.dreport-row2");
        //标题添加到第一行
        List<String> titleList = new ArrayList<String>();
        int n = titles.size();
        //由于之前有一个dreport-title的td，所以从第二个开始遍历
        for (int i = 1; i < n; i++) {
            titleList.add(titles.get(i).text());
            //log.debug("标题" + titles.get(i).text());
        }
        rate.add(titleList);

        //12+1列
        n= dates.size();
        for (int i = 0; i < n; i++) {
            List<String> contentList = new ArrayList<String>();
            contentList.add(dates.get(i).text());
            //log.debug("日期：" + dates.get(i).text());
            for (int j = 0; j < 12; j++) {
                contentList.add(contents.get(i*12 + j).text());
                //log.debug("添加汇率：" + contents.get(i*12 + j).text());
            }
            rate.add(contentList);
        }

        return rate;
    }

    //原list中包含所有数据，现将list中数据修改为题目要求输出的数据
    private static List<List<String>> dealListDate(List<List<String>> list) {
        List<List<String>> threeRateList = new ArrayList<List<String>>(timesLength);
        int n = list.size();
        int m = list.get(0).size();
        for (int i = 0; i < n; i++) {
            List<String> content = new ArrayList<String>();
            for (int j = 0; j < m; j++) {
                //取0,1,2,4，列的数据
                if (j < 3 || j == 4) {
                    content.add(list.get(i).get(j));
                }
            }
            threeRateList.add(content);
        }
        return threeRateList;
    }

    /**
     * 将list中的数据写入到指定path路径的文件中
     * @param list  要写入数据源
     * @param path 路径，包含文件名
     * @return 成功 true，失败 false
     */
    protected static boolean printToExcel(List<List<String>> list, String path) {
        File excel = new File(path);
        try {
            WritableWorkbook wwb = Workbook.createWorkbook(excel);
            WritableSheet sheet = wwb.createSheet("exchangeRate", 0);
            int j, i = 0;
            for (List<String> r : list) {
                j = 0;
                for (String c : r) {
                    Label label = new Label(j, i, c);
                    sheet.addCell(label);
                    j++;
                }
                i++;
            }
            wwb.write();
            wwb.close();
        } catch (IOException e) {
            log.info(e.getMessage());
            return false;
        } catch (WriteException e) {
            log.info(e.getMessage());
            return false;
        }
        return true;
    }
}
