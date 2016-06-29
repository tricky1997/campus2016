package ExchangeRate;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.htmlparser.*;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lz on 16-6-22.
 */

public class ExchangeRate
{
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    public static String url = "http://www.fert.cn/news/?q=%C8%CB%C3%F1%B1%D2%BB%E3%C2%CA%D6%D0%BC%E4%BC%DB%B9%AB%B8%E6&address=";
    public static Map<String,String> links = new HashMap<String,String>();

    ExchangeRate()
    {
        this.getRates();
    }

    public void getSubURLs()
    {
        try {
            Parser parser = new Parser(url);
            parser.setEncoding("gb2312");//设置为国标，否则有乱码，也可以为GB
            NodeFilter frameNodeFilter = new NodeFilter() {
                @Override
                public boolean accept(Node node) {
                    if (node.getText().startsWith("frame src=")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };

            NodeFilter aNodeFilter = new NodeClassFilter(LinkTag.class);
            OrFilter linkFilter = new OrFilter(frameNodeFilter, aNodeFilter);
            NodeList nodeList = parser.extractAllNodesThatMatch(linkFilter);

            for(int i = 0; i<nodeList.size();i++){
                Node node = nodeList.elementAt(i);
                String linkURL = "";
                if(node instanceof LinkTag){
                    LinkTag link = (LinkTag)node;
                    linkURL= link.getLink();
                }
                else
                {
                    String nodeText = node.getText();
                    int beginPosition = nodeText.indexOf("src=");
                    nodeText = nodeText.substring(beginPosition);
                    int endPosition = nodeText.indexOf(" ");
                    if(endPosition == -1){
                        endPosition = nodeText.indexOf(">");
                    }
                    linkURL = nodeText.substring(5, endPosition - 1);
                }
                LinkTag link = (LinkTag)node;

                if(link.getLinkText().contains("人民币汇率中间价公告"))
                {
                    String regEx = "\\d[\\u4e00-\\u9fa5]\\d+[\\u4e00-\\u9fa5]";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(link.getLinkText());
                    if(m.find())
                        links.put(m.group(0),link.getLink());
                }
            }

        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    public void getRates()//
    {
        getSubURLs();

        String filePath="/home/lz/ExchangeRate.xls";//文件路径
        HSSFWorkbook workbook = new HSSFWorkbook();//创建Excel文件(Workbook)
        HSSFSheet sheet = workbook.createSheet("ExchangeRate");//创建工作表(Sheet)
        int lineCount=1;

        for (Map.Entry<String, String> e : links.entrySet())
        {
            try {
                Parser parser = new Parser(e.getValue());
                parser.setEncoding("gb2312");
                NodeFilter frameNodeFilter = new NodeFilter()
                {
                    @Override
                    public boolean accept(Node node)
                    {
                        if (node.getText().startsWith("frame src="))
                        {
                            return true;
                        }
                        else
                        {
                            return false;
                        }
                    }
                };

                NodeFilter aNodeFilter = new NodeClassFilter(Div.class);
                NodeList nodeList = parser.extractAllNodesThatMatch(aNodeFilter);

                HSSFRow row = sheet.createRow(0);
                row.createCell(0).setCellValue("日期");
                row.createCell(1).setCellValue("美元");
                row.createCell(2).setCellValue("欧元");
                row.createCell(3).setCellValue("港元");

                for(int i = 0; i<nodeList.size();i++)
                {
                    Node node = nodeList.elementAt(i);
                    //System.out.println(node.toHtml());

                    if(node.toHtml().contains("外汇市场人民币汇率中间价"))
                    {
                        String dollar = regexRate("美元",node.toHtml());
                        String euro = regexRate("欧元",node.toHtml());
                        String hk = regexRate("港元",node.toHtml());
                        //System.out.println(dollar+euro+hk);
                        try
                        {
                            row = sheet.createRow(lineCount);// 创建行,从1开始
                            row.createCell(0).setCellValue(e.getKey());// 设置单元格内容,重载
                            row.createCell(1).setCellValue(dollar);// 设置单元格内容,重载
                            row.createCell(2).setCellValue(euro);// 设置单元格内容,重载
                            row.createCell(3).setCellValue(hk);// 设置单元格内容,重载
                        }
                        catch (Exception e2)
                        {

                        }
                        break;
                    }
                }
            }
            catch (ParserException e3)
            {
                e3.printStackTrace();
            }
            lineCount++;
        }

        try
        {
            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);//保存Excel文件
            out.close();//关闭文件流
        }
        catch (Exception e3)
        {
            e3.printStackTrace();
        }
        System.out.println("All Done");
    }

    public String regexRate(String str,String s)
    {
        String regexWhole = "1"+str+"对人民币[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*";
        String regexRates = "[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*";
        String tempWhole;
        String temp = "";
        Pattern p = Pattern.compile(regexWhole);
        Matcher m = p.matcher(s);
        if(m.find())
        {
            tempWhole = m.group();
            p = Pattern.compile(regexRates);
            m = p.matcher(tempWhole);
            if(m.find())
                temp = m.group();
        }
        return temp;
    }

    public static void main(String[] args)//
    {
        ExchangeRate er = new ExchangeRate();
    }
}
