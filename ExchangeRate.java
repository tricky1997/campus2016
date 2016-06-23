package com.qunar;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hanju on 2016/6/21.
 */
public class ExchangeRate {
    private String filePath="rate.xls";
    //获取汇率网站
    private String url1 = "http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/17105/index1.html";
    private String url2="http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/17105/index2.html";
    //保存最终得到的汇率中间价，第一个值为日期，list存储三个货币对应的汇率
    private List<List<String>> pageContent;
    //根据当前日期，获取要爬取的近一个月内的数据链接
    private List<String> finalUrls=new ArrayList<String>();
    //记录当前时间30天前的时间日期
    private int YEAR=0;
    private int MONTH=0;
    private int DAY=0;

    public ExchangeRate()
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        //30天前
        calendar.add(Calendar.DAY_OF_MONTH,-30);
        YEAR=calendar.get(Calendar.YEAR);
        MONTH=(calendar.get(Calendar.MONTH)+1);
        DAY=calendar.get(Calendar.DAY_OF_MONTH);

    }

    public void getRateAndExport() {
        List<List<String>> Rates=getRate();
        //写入xls文件
        WritableWorkbook wwb=null;
        try {
            OutputStream os=new FileOutputStream(filePath);
            wwb = Workbook.createWorkbook(os);
            jxl.write.WritableSheet ws = wwb.createSheet("汇率中间价", 0);
            //写入每列的名称
            ws.addCell(new Label(1,0,"美元"));
            ws.addCell(new Label(2,0,"欧元"));
            ws.addCell(new Label(3,0,"港元"));
            int i=1;
            for (List<String> list:Rates
                    ) {
                for(int j=0;j<4;j++)
                {
                    ws.addCell(new Label(j,i,list.get(j)));
                }
                i++;
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
        finally {
            if(wwb!=null)
            {
                try {
                    wwb.write();
                    wwb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }

            }

        }
    }
    //获得指定url集合中的汇率
    private List<List<String>> getRate() {
        List<List<String>> result=new ArrayList<List<String>>();
        for(String url:getFinalUrls())
        {
            List<String> oneRate=new ArrayList<String>();

            HtmlPage page=getPage(url);
            String content=page.getElementById("zoom").asText();

            String date="",dollar="",europ="",hong="";
            String[] words=content.split("，");
            for(String word:words)
            {
                if (word.contains("美元"))
                {
                    date=word.substring(0,word.indexOf("日")+1);
                    dollar=word.substring(word.indexOf("美元对人民币")+6,word.lastIndexOf("元"));
                    continue;
                }
                if (word.contains("欧元"))
                {
                    europ=word.substring(word.indexOf("欧元对人民币")+6,word.lastIndexOf("元"));
                    continue;
                }
                if (word.contains("港元"))
                {
                    hong=word.substring(word.indexOf("港元对人民币")+6,word.lastIndexOf("元"));
                    continue;
                }
            }
            oneRate.add(date);
            oneRate.add(dollar);
            oneRate.add(europ);
            oneRate.add(hong);
            result.add(oneRate);
        }
        return result;
    }

    //根据当前日期与指定网站，获得一个月内的汇率中间价网址
    private List<String> getFinalUrls() {
        //根据url指定网站，获取前两页的汇率网页链接
        finalUrls.addAll(getList(url1));
        finalUrls.addAll(getList(url2));
        return finalUrls;
    }
    private HtmlPage getPage(String url)
    {
        HtmlPage page=null;
        WebClient webClient=new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(100000);
        webClient.getOptions().setDoNotTrackEnabled(false);
        try {
            page=webClient.getPage(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }
    //获得符合条件的公告列表
    private List<String> getList(String url) {
        List<String> tmpUrls=new ArrayList<String>();
        HtmlPage page=getPage(url);
        DomNodeList<DomElement> links=page.getElementsByTagName("a");
        for (DomElement link: links
                ) {
            if(link.asText().contains("中国外汇交易中心受权公布人民币汇率中间价公告"))
            {

                //根据标题中的时间，与30天前进行对比

                int year=Integer.parseInt(link.asText().substring(0,link.asText().indexOf("年")));
                int month=Integer.parseInt(link.asText().substring(link.asText().indexOf("年")+1,link.asText().indexOf("月")));
                int day=Integer.parseInt(link.asText().substring(link.asText().indexOf("月")+1,link.asText().indexOf("日")));
                if(YEAR<year||(YEAR==year&&MONTH<month)||(YEAR==year&&MONTH==month&&DAY<=day))
                {
                    String aUrl=link.getAttribute("href");
                    if(!aUrl.contains("http://www.pbc.gov.cn"))
                    {
                        aUrl="http://www.pbc.gov.cn"+aUrl;
                    }
                    tmpUrls.add(aUrl);
                }
                //System.out.println(link.asText()+" "+link.getAttribute("href"));
            }

        }
        return tmpUrls;
    }

    public String getTime() {
        return YEAR+"年"+MONTH+"月"+DAY+"日";
    }
}
