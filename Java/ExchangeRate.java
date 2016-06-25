package com.qunar.dan;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dan on 2016/6/22.
 */
public class ExchangeRate {
    Pattern doubleNumberPattern = Pattern.compile("[0-9]+\\.[0-9]+");
    Pattern datePattern = Pattern.compile("[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日");
    Pattern exchangeRateItemPattern = Pattern.compile("1.{2,6}[对].{6,12}元");

    public double findDigitalNumber(String source) {
        double res = 0.0;

        Matcher m = doubleNumberPattern.matcher(source);
        if(m.find()) {
            res = Double.parseDouble(m.group());
        }

        return res;
    }

    public String findDate(String source){
        String res = "";

        Matcher m = datePattern.matcher(source);
        if(m.find()) {
            res = m.group();
        }

        return res;
    }

    public ArrayList<Double> getExchangeRate(String url, ArrayList<String> currencySelector) {
        ArrayList<Double> res = new ArrayList<>();
        Document doc = null;

        for (int i = 0;i < currencySelector.size();++i) {
            res.add(0.0);
        }

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements newsHeadlines = doc.select("#zoom p");//p tag after #zoom id
        String article = newsHeadlines.html();

        Matcher m = exchangeRateItemPattern.matcher(article);

        while(m.find()) {
            String tmp = m.group();
            for(int i = 0;i < currencySelector.size();++i) {
                if (tmp.contains(currencySelector.get(i))) {
                    //System.out.println("美元");
                    res.set(i,findDigitalNumber(tmp));
                }
            }
        }

        return res;
    }

    public ArrayList<ExchangeRateBean> getLinks(String homeLinks,int Number) {
        ArrayList<ExchangeRateBean> res = new ArrayList<ExchangeRateBean>();
        String officialSite = "http://www.pbc.gov.cn";
        Document doc = null;

        int index = 0;
        int pageIndex = Character.getNumericValue(homeLinks.charAt(homeLinks.length()-6));

        while(index < Number) {
            try {
                doc = Jsoup.connect(homeLinks.substring(0,homeLinks.length()-6)+pageIndex+homeLinks.substring(homeLinks.length()-5,homeLinks.length())).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements newsHeadlines = doc.select("font.newslist_style a");//p tag after #zoom id
            for (int i = 0; i < newsHeadlines.size(); ++i) {
                if(index < Number) {
                    String article = newsHeadlines.get(i).attr("href");
                    ExchangeRateBean erbean = new ExchangeRateBean();
                    erbean.setDate(findDate(newsHeadlines.get(i).html()));
                    erbean.setLink(officialSite + article);
                    res.add(erbean);
                    //System.out.println(article);
                    ++index;
                } else break;
            }
            ++pageIndex;
        }

        return res;
    }

    public void storeToExcel(ArrayList<ExchangeRateBean> exchangeRate,String storePath){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Currency Exchange Rate");

        Row row = sheet.createRow(0);//start at 0

        Cell cell = row.createCell(0);
        cell.setCellValue("日期");

        for(int i = 0;i < ExchangeRateBean.getCurrencyName().size();++i) {
            cell = row.createCell(i+1);//start at 0
            cell.setCellValue(ExchangeRateBean.getCurrencyName().get(i));
        }

        int rowNumber = 1;
        for(ExchangeRateBean erbean : exchangeRate) {
            row = sheet.createRow(rowNumber);
            cell = row.createCell(0);
            cell.setCellValue(erbean.getDate());

            ArrayList<Double> exRate = erbean.getCurrencyValue();
            for (int i = 0; i < exRate.size(); ++i) {
                cell = row.createCell(i + 1);
                cell.setCellValue(exRate.get(i));
            }
            ++rowNumber;
        }


        //Write data into xlsx file
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(storePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(out != null){
            try {
                workbook.write(out);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String args[]){
        String Home = "http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/17105/index1.html";

        ExchangeRate dan = new ExchangeRate();
        ArrayList<String> currrency = new ArrayList<>();

        currrency.add("美元");
        currrency.add("欧元");
        currrency.add("港元");

        ExchangeRateBean.setCurrencyName(currrency);

        // Step 1: get links of recent 30 days
        ArrayList<ExchangeRateBean> results = dan.getLinks(Home,30);
        System.out.println("Obtained Links!");

        // Step 2: craw data of certain day
        for(int i = 0;i < results.size();++i) {
            results.get(i).setCurrencyValue(dan.getExchangeRate(results.get(i).getLink(),ExchangeRateBean.getCurrencyName()));
        }
        System.out.println("Data Crawing Finished!");

        // Step 3: store crawed exchange rate data into excel file
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String storePath = sdf.format(cal.getTime()) + "-最近30天汇率信息.xls";

        dan.storeToExcel(results,storePath);

        System.out.println("Stored Results!");

    }
}