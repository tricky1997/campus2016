/**
 * 二、分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，
 * 得到人民币对美元、欧元、港币的汇率，形成excel文件输 出。汇率数据找相关的数据源，
 * 自己爬数据分析。
 * Created by jianghp on 2016-06-15.
 * get data from html files and then write data to excel table with apache poi
 *
 <dependency>
 <groupId>org.apache.poi</groupId>
 <artifactId>poi</artifactId>
 <version>3.12</version>
 </dependency>
 <dependency>
 <groupId>org.jsoup</groupId>
 <artifactId>jsoup</artifactId>
 <version>1.9.2</version>
 </dependency>
 */
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExchangeRate3 {
    private static String classPath = ExchangeRate3.class.getResource("/").getFile();

    public static void main(String[] args) {
        List<List<String>> res = getData30Days();
        writeToExcel(res,"ExchangeRate3.xls");
    }


    /**
     * 输出结果到excel表中
     * @param res ，汇率中间价数据
     * @param fileName，输出文件名
     */
    public static void writeToExcel(List<List<String>> res,String fileName) {

        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");
        try {
            //表头信息
            Row row = sheet.createRow(0);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("日期Date");
            Cell cell2 = row.createCell(1);
            cell2.setCellValue("美元USD");
            Cell cell3 = row.createCell(2);
            cell3.setCellValue("欧元EUR");
            Cell cell4 = row.createCell(3);
            cell4.setCellValue("港币HKD");
            //获取日期字符串列表
            List<String> days = res.get(0);
            //输出每一天的数据
            for (int i=1;i<res.size();i++){
                List<String> data = res.get(i);
                Row r = sheet.createRow(i);
                //输出日期
                int j=0;
                Cell c1 = r.createCell(j);
                c1.setCellValue(days.get(i-1));
                //输出汇率中间价
                for(String d:data){
                    j++;
                    Cell c = r.createCell(j);
                    c.setCellValue(d);
                }
            }
            FileOutputStream fileOut = new FileOutputStream(classPath+fileName);
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取过去30天的汇率中间价数据
     * @return List<List<String>> ， 汇率中间价列表，第一项为日期字符串列表，之后是汇率中间价列表项
     */
    public static List<List<String>> getData30Days() {
        List<List<String>> list = new ArrayList<>();
        //获取过去30天的日期字符串，并加入到列表中
        List<String> days = getLast30Days();
        list.add(days);
        //获取过去29天的数据
        String url = "";
        for(int i=0;i<29;i++){
            url = "http://www.kuaiyilicai.com/bank/rmbfx/b-safe.html?querydate="+days.get(i);
            List<String> data = getDataOneDay(url);
            if (data!=null){
                list.add(data);
            }
        }
        //获取当天的数据
        url = "http://www.kuaiyilicai.com/bank/rmbfx/b-safe.html";
        List<String> dataToday = getDataOneDay(url);
        if (dataToday!=null){
            list.add(dataToday);
        }
        return list;
    }

    /**
     * 获取一天的汇率中间价数据，适用于网站http://www.kuaiyilicai.com/bank/rmbfx/b-safe.html
     * @param url ， 汇率中间价网址
     * @return List<String>， 汇率中间价（美元，欧元，港币）数据
     */
    public static List<String> getDataOneDay(String url){
        List<String> res = null;
        try {
            //从网址获取Document对象
            Document doc = Jsoup.connect(url).get();
            //CSS选择器形式选择所需数据所在行
            Elements trs = doc.select("table tr");
            //获取数据
            String hkd =trs.get(1).select("td").get(1).html();
            String usd =trs.get(2).select("td").get(1).html();
            String eur =trs.get(3).select("td").get(1).html();
            res = new ArrayList<>();
            res.add(usd);
            res.add(eur);
            res.add(hkd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 获取过去30天的日期字符串，格式为yyyy-MM-dd
     * @return List<String>， 日期字符串列表
     */
    public static List<String> getLast30Days(){
        List<String> res = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        Date end= c.getTime();
        c.add(Calendar.DATE,-29);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i=0;i<30;i++) {
            Date date = c.getTime();
            String dateString = sdf.format(date);
            res.add(dateString);
            c.add(Calendar.DATE,1);
        }
        return res;
    }
}
