package com.qunar.helang.exchangeRate;

/**
 * Created by lactic_h on 6/30/16.
 */



import java.io.*;
import java.util.*;
import java.util.regex.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.http.client.ClientProtocolException;



public class RecordExchangeRate {

    public static void main(String[] args) throws ClientProtocolException, IOException
    {
        Queue<String> urlQueue= getURLs();
        Iterator<String> it=urlQueue.iterator();

        try {
            WritableWorkbook book= Workbook.createWorkbook(new File("ExchangeRate.xls"));
            WritableSheet sheet=book.createSheet("sheet1",0);
            sheet.addCell(new Label(0,0,"date"));
            sheet.addCell(new Label(1,0,"Dollar"));
            sheet.addCell(new Label(2,0,"Euro"));
            sheet.addCell(new Label(3,0,"HK Dollar"));
            int lineIndex=1;
            while(it.hasNext()){
                String[] result=getInfo(it.next());
                for(int i=0; i<4; i++){
                    sheet.addCell(new Label(i,lineIndex,result[i]));
                }
                lineIndex++;
            }

            book.write();
            book.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Queue<String> getURLs()throws ClientProtocolException, IOException
    {

        Queue<String> urlQueue= new LinkedList<String>();

        String prefix="http://www.pbc.gov.cn";

        String url="http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/index.html";
        String html= GetHtml.getHtml(url);

        Document doc = Jsoup.parse(html);
        Elements es=doc.select("font[class*=newslist_style]");
        for(Element e: es){
            String cUrl=prefix+e.children().first().attr("href");
            urlQueue.add(cUrl);
            System.out.println(cUrl);
            // System.out.println(e.children().first().attr("href"));
            // System.out.println(e.nextElementSibling().ownText());
        }
        return urlQueue;
    }

    public static String[] getInfo(String url)throws ClientProtocolException, IOException
    {
        String html= GetHtml.getHtml(url);

        String p="中国人民银行授权中国外汇交易中心公布，(.*?)银行.*1美元对人民币(\\d+\\.*\\d*).*1欧元对人民币(\\d+\\.*\\d*).*1港元对人民币(\\d+\\.*\\d*)";

        Pattern pattern= Pattern.compile(p);
        Matcher matcher= pattern.matcher(html);
        String[] result= new String[4];
        if(matcher.find()){
            result[0]=matcher.group(1);
            result[1]=matcher.group(2);
            result[2]=matcher.group(3);
            result[3]=matcher.group(4);

            // 	System.out.println(matcher.group(1));
            // 	System.out.println(matcher.group(2));
            // 	System.out.println(matcher.group(3));
            // 	System.out.println(matcher.group(4));

        }else{
            System.out.println("false");
        }
        return result;
    }
}



