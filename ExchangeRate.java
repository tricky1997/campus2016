/**
* 程序解决的问题：分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，得到人民币对美元、欧元、港币的汇率，形成excel文件输出。
* 数据来源：http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action

Created by liwen on 2016/6/21.
*/
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExchangeRate 
{
    /**
     * 数据来源，即查询网址
     */
    private static final String EXCHANGERATE_WEBSITE = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action";
	
   /**
    * 得到所需汇率数据并制作为Excel报表
    * @param queryParameter 为爬取数据设定的URL
    */
    public void getExchangeRateData(String queryParameter)
    {
    	  //与网站建立链接，爬取网站数据
    	  Connection connection =  Jsoup.connect(EXCHANGERATE_WEBSITE+queryParameter);//获得网站的Connection接口
    	  Document document = null;  //存储网页HTML数据的文件
    	    try{
    	    	document = connection.timeout(10000).get(); //得到网页HTML代码
    	    }
    	    catch(IOException e){
    	    	e.printStackTrace();
    	    	System.out.println("Connection error!");
    	    }
    	    
    	  //提取日期，美元、欧元、港币对人民币汇率数据
    	    Elements elements = document.getElementsByClass("first");//获取30天的人民币汇率中间价数据
            String exchangeRateData[][] = new String[4][30];
            for(int i = 0; i < 30; i++){
                exchangeRateData[0][i] = elements.get(i).child(0).text();
                exchangeRateData[1][i] = elements.get(i).child(1).text();
                exchangeRateData[2][i] = elements.get(i).child(2).text();
                exchangeRateData[3][i] = elements.get(i).child(4).text();
            }
           
            
          //将数据存储到Excel文件中
            WritableWorkbook exchangeRateExcel = null;
            FileOutputStream outputStream = null;
            try
    		{
            	outputStream = new FileOutputStream(new File("ExchangeRateExcel.xls"));
    			exchangeRateExcel = Workbook.createWorkbook(outputStream);
    			//创建人民币汇率表单并设置单元格格式
    	        WritableSheet sheetOfExchangeRateExcel = exchangeRateExcel.createSheet("人民币汇率中间价列表", 1);
    	        WritableFont writableFont = new WritableFont(WritableFont.TIMES, 12, WritableFont.NO_BOLD, true);
                WritableCellFormat writableCellFormat = new WritableCellFormat(writableFont);
                writableCellFormat.setAlignment(jxl.format.Alignment.CENTRE);

    	        //设置表头
                sheetOfExchangeRateExcel.addCell(new Label(0,0,"日期", writableCellFormat));
                sheetOfExchangeRateExcel.addCell(new Label(1,0,"美元", writableCellFormat));
                sheetOfExchangeRateExcel.addCell(new Label(2,0,"欧元", writableCellFormat));
                sheetOfExchangeRateExcel.addCell(new Label(3,0,"港币", writableCellFormat));
                
                //向表格指定位置写入数据
                for(int i = 0; i < 30; i++){
                    for(int j = 0; j < 4; j++){
                    	sheetOfExchangeRateExcel.addCell(new Label(j, 1+i,exchangeRateData[j][i]));
                    }
                }
    	        
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    		}
            finally {
            	try {
                    if(exchangeRateExcel != null) {
                    	exchangeRateExcel.write();
                    	exchangeRateExcel.close();
                    }
                    if(outputStream != null) {
                        outputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 	    
    }
    
   
    
    public static void main(String[] args)
     {
    	 //设置日期格式
	     SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("yyyy-MM-dd");
	     Date date = new Date();
	     //获得当天日期，即为网页中查找结束日期
	     String endtimeOfQuery = simpleDateFormat.format(date);
	     //获得查找起始日期
	     String starttimeOfQuery = simpleDateFormat.format(date.getTime()-50*24*60*60*1000L);
	     //为爬取数据设定的URL
	     String queryParameter = EXCHANGERATE_WEBSITE+"?"+"projectBean.startDate=" + starttimeOfQuery + "&projectBean.endDate=" + endtimeOfQuery;
	    
	     ExchangeRate exchangeRate  = new ExchangeRate();
	     //从网页爬取所需数据，根据获取的数据做表格	    
	     exchangeRate.getExchangeRateData(queryParameter);
	     
     }
}
