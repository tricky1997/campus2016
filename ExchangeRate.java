import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 二、分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，得到人民币对美元、欧元、港币的汇率，形成excel文件输出。
 汇率数据找相关的数据源，自己爬数据分析。（作业命名：ExchangeRate）
 * Created by MaNa on 2016/6/13.
 */
public class ExchangeRate{
    //中国人民银行近30天人民币汇率中间价（两页对应url),fileName为要输出的excel文件名
    private static String URL = "http://wzdig.pbc.gov.cn:8080/dig/ui/search.action?ty=&w=false&f=&dr=true&p=1&sr=score+desc%2Cdatetime+desc&rp=&advtime=&advrange=&fq=&ext=&q=%E6%B1%87%E7%8E%87%E4%B8%AD%E9%97%B4%E4%BB%B7.html";
    private static String URL2="http://wzdig.pbc.gov.cn:8080/dig/ui/search.action?ty=&w=false&f=&dr=true&p=2&sr=score+desc%2Cdatetime+desc&rp=&advtime=&advrange=&fq=&ext=&q=%E6%B1%87%E7%8E%87%E4%B8%AD%E9%97%B4%E4%BB%B7";
    private static String fileName="F:/rate.xls";
    public static void main(String[] args) throws SAXException, IOException {
        File excelFile=new File(fileName);
        FileOutputStream output = new FileOutputStream(excelFile);
        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet=workbook.createSheet();
        /*
        *excel第一行对列数据进行说明
        */
        HSSFRow row1=sheet.createRow(0);
        HSSFCell dateCel=row1.createCell(0);
        dateCel.setCellValue(new HSSFRichTextString("汇率换算日期"));
        HSSFCell dollarCel=row1.createCell(1);
        dollarCel.setCellValue(new HSSFRichTextString("1美元"));
        HSSFCell eurCel=row1.createCell(2);
        eurCel.setCellValue(new HSSFRichTextString("1欧元"));
        HSSFCell yenCel=row1.createCell(3);
        yenCel.setCellValue(new HSSFRichTextString("1港币"));
         /*HSSFCell hkdollarCel=row1.createCell(4);
         hkdollarCel.setCellValue(new HSSFRichTextString("1港币"));*/

        text.getResultRateList(URL,sheet);
        text.getResultRateList(URL2,sheet);
        //将输出流关闭
        workbook.write(output);
        output.close();
    }

    /*
    * getResultRateList方法获取人民币汇率中间价
    *
    */
    public static void getResultRateList(String url,HSSFSheet sheet) throws IOException {
        //美元，欧元，日元，港币
        double dollar=0, euro=0, yen=0, hkdollar=0;
        //汇率日期
        String rateOfDay=null;
        List<String> list = new ArrayList<String>();
        /*
        * 获取目标url网页内容
        */
        WebClient webClient = new WebClient();
        WebClientOptions clientOptions = webClient.getOptions();
        // 设置webClient的相关参数
        clientOptions.setJavaScriptEnabled(true);
        clientOptions.setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        clientOptions.setTimeout(5000);
        clientOptions.setThrowExceptionOnScriptError(false);
        // 模拟浏览器打开目标网址
        HtmlPage rootPage = webClient.getPage(url);
        HtmlElement htmlElement = rootPage.getBody();
        String xmlContent = htmlElement.asXml();
        Document document = Jsoup.parse(xmlContent);
        //格式化输出汇率
        DecimalFormat df = new DecimalFormat("######0.0000");

        /*
        * 抓取网页中需要的汇率信息，将汇率写入到excel文件中
        *
        */
        for (int i = 0; i < 10; i++) {
            String dataByDay = document.select("p").get(i).text();
            String[] datas = dataByDay.split("，");
            String[] spilt = datas[1].split("：");
            rateOfDay = spilt[0].substring(0, spilt[0].indexOf("银行"));
            list.add(rateOfDay);
            list.add(spilt[1]);
            list.add(datas[2]);
            list.add(datas[3]);
            list.add(datas[4]);
            dollar = Double.parseDouble(spilt[1].substring(7, spilt[1].length() - 1));
            euro = Double.parseDouble(datas[2].substring(7, datas[2].length() - 1));
            yen=Double.parseDouble(datas[3].substring(9,datas[3].length()-1));
            //在excel文件中添加数据
            HSSFRow hssfRow= sheet.createRow(sheet.getLastRowNum()+1);
            hssfRow.createCell(0).setCellValue(rateOfDay);
            hssfRow.createCell(1).setCellValue(df.format(dollar));
            hssfRow.createCell(2).setCellValue(df.format(euro));
            hssfRow.createCell(3).setCellValue(df.format(yen));
            //hssfRow.createCell(4).setCellValue(df.format(hkdollar))
        }
    }
}