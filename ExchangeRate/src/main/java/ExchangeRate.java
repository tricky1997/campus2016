import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.poi.hssf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daihy on 2016/6/19.
 * Statistical exchange rate of RMB to dollar,euro, hk dollar in last 30 days.
 * Calculate average number and write it in an excel file.
 * Use htmlunit to get web page, JSoup to parse page.
 * Use POI to handle excel problem
 */
public class ExchangeRate {
    /*get data from official site of People's Bank of China*/
    private static final String url1 = "http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/17105/index1.html";
    private static final String url2 = "http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/17105/index2.html";
    private static final String rootUrl = "http://www.pbc.gov.cn";
    private List<String> targetURLs = new ArrayList<String>();
    /*sum of exchange rate in 30 days*/
    private double dollar = 0, euro = 0, hkd = 0;
    /*average of exchange rate*/
    private double avgDollar = 0, avgEuro = 0, avgHkd = 0;

    public static void main(String[] args) {
        ExchangeRate ER = new ExchangeRate();
        ER.getURLFromRootSite(url1, 20);
        ER.getURLFromRootSite(url2, 10);

        for (String url : ER.targetURLs) {
            ER.getExchangeRateFromPage(url);
        }
        ER.getAvgNum();
        ER.writeXLS();
        System.out.println("1美元兑换人民币： " + ER.avgDollar + " 1欧元兑换人民币： " + ER.avgEuro + " 1港元兑换人民币： " + ER.avgHkd);
    }

    /**
     * Get 30 urls from root page, these pages contain exchange rate information
     *
     * @param root url1 and url2
     * @param cnt  count of sub urls get from root page
     */
    private void getURLFromRootSite(String root, int cnt) {
        Document doc = null;
        try {
            WebClient wc = new WebClient();
            HtmlPage page = wc.getPage(root);
            String pageXml = page.asXml();
            doc = Jsoup.parse(pageXml);

            Elements eles = doc.select("div[opentype=page]").get(0).select("table").get(0).select("tbody").get(0).select("tr").get(1).select("td").get(0).select("table");

            for (int i = 0; i < cnt; i++) {
                Element tmp = eles.get(i).select("a").get(0);
                String link = tmp.attr("href");
                targetURLs.add(rootUrl + link);
            }
        } catch (IOException e) {
            System.out.println("can not open root page!");
        }
    }

    /**
     * Get exchange rate of each day
     * @param url sub url which contains exchange rate info
     */
    private void getExchangeRateFromPage(String url) {
        Document doc = null;
        try {
            WebClient wc = new WebClient();
            HtmlPage page = wc.getPage(url);
            String pageXml = page.asXml();
            doc = Jsoup.parse(pageXml);

            String text = doc.select("div#zoom").get(0).select("p").get(0).text();

            dollar += Double.parseDouble(text.substring(text.indexOf("1美元") + 7, text.indexOf("1美元") + 13));
            euro += Double.parseDouble(text.substring(text.indexOf("1欧元") + 7, text.indexOf("1欧元") + 13));
            hkd += Double.parseDouble(text.substring(text.indexOf("1港元") + 7, text.indexOf("1港元") + 13));
        } catch (IOException e) {
            System.out.println("can not open root page!");
        }
    }

    /**
     * Calculate average rate.
     */
    private void getAvgNum() {
        avgDollar = (double) (Math.round(dollar * 10000 / 30) / 10000.0);
        avgEuro = (double) (Math.round(euro * 10000 / 30) / 10000.0);
        avgHkd = (double) (Math.round(hkd * 10000 / 30) / 10000.0);
    }

    /**
     * write result in an excel
     */
    private void writeXLS() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet");
        HSSFRow row = sheet.createRow(0);
        String[] headers = new String[]{"1美元对人民币", "1欧元对人民币", "1港元对人民币"};
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        HSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue(avgDollar);
        row2.createCell(1).setCellValue(avgEuro);
        row2.createCell(2).setCellValue(avgHkd);

        try {
            FileOutputStream fos = new FileOutputStream("ExchangeRate.xls");
            wb.write(fos);
        } catch (FileNotFoundException e) {
            System.out.println("file not found!");
        } catch (IOException e) {
            System.out.println("write mistake!");
        }
    }
}
