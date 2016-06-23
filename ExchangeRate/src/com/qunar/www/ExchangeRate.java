package com.qunar.www;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import jxl.Workbook;
import jxl.CellType;  
import jxl.Workbook;  
import jxl.format.*;

import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class ExchangeRate {
	public static void main(String[] args) throws Exception, IOException, Exception {
		String url1 = "http://finance.sina.com.cn/roll/2016-03-14/doc-ifxqhnev6017646.shtml";
		String url2 = "http://finance.sina.com.cn/roll/2016-03-23/doc-ifxqnnkr9920662.shtml";
		String url3 = "http://finance.sina.com.cn/roll/2016-03-25/doc-ifxqsxic3198645.shtml";
		String url4 = "http://finance.sina.com.cn/roll/2016-03-30/doc-ifxqssxu8602556.shtml";
		String url5 = "http://finance.sina.com.cn/roll/2016-03-28/doc-ifxqssxu8401728.shtml";
		String url6 = "http://finance.sina.com.cn/roll/2016-04-06/doc-ifxqxqmf4107501.shtml";
		String url7 = "http://finance.sina.com.cn/roll/2016-04-08/doc-ifxrcuyk2444517.shtml";
		String url8 = "http://finance.sina.com.cn/roll/2016-04-14/doc-ifxrcuyk2926663.shtml";
		String url9 = "http://finance.sina.com.cn/roll/2016-04-15/doc-ifxriqri6348475.shtml";
		String url10 = "http://finance.sina.com.cn/roll/2016-04-18/doc-ifxrizpp1588973.shtml";
		
		
		
		
		String[] url = {url1,url2,url3,url4,url5,url6,url7,url8,url9,url10};
		Document[] doc = getConnection(url);
		String str = process(doc);
		//System.out.println(str);
		ExportExcel(str);
	}
	
	public static Document[] getConnection(String[] url) {
		Document[] doc = new Document[10];
		try {
			for(int i = 0; i < doc.length; i++) {
				doc[i] = Jsoup.connect(url[i]).get();
			}
			//System.out.println(doc[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	public static String process(Document[] doc) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sbd = new StringBuilder();
		Element[] e = new Element[10];
		for(int i = 0; i < doc.length; i++) {
			e[i] = doc[i].select("meta").attr("name", "description").get(5);
			String[] str1 = e[i].attr("content").split("£¬");
			String[] str2 = str1[1].split("£º");
			sb.append(str2[1].substring(7, str2[1].length())+";");
			sb.append(str1[2].substring(7, str1[2].length()) + ";");
			sb.append(str1[4].substring(7, str1[4].length()));
	
			sbd.append(sb + ";");
		    sb.delete(0, sb.length());
		}
		sbd.deleteCharAt(sbd.length() - 1);
		return sbd.toString();
	}
	
	public static void ExportExcel(String str) {
		String[] title = {"1USD to RMB", "1Euro to RMB", "1Hong Kong dollar to RMB" };
		File f = new File("test.xls");
		if(f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
			WritableWorkbook workbook = Workbook.createWorkbook(f);
			WritableSheet sheet = workbook.createSheet("sheet1", 0);
			Label label = null;
			for(int i = 0; i < title.length; i++) {
				sheet.setColumnView(i, 15);
				
				label = new Label(i,0,title[i], setCellFormat());
				try {
					sheet.addCell(label);
				} catch (RowsExceededException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String[] rate = str.split(";");
			for(int j = 1; j <= rate.length/3; j++) {
			
				try {
					label = new Label(0/*Row*/, j/*Column*/, rate[3*j - 3], setCellFormat());
					sheet.addCell(label);
					label = new Label(1/*Row*/, j/*Column*/, rate[3*j- 3 + 1], setCellFormat());
					sheet.addCell(label);
					label = new Label(2/*Row*/, j/*Column*/, rate[3*j - 3 + 2], setCellFormat());
					sheet.addCell(label);
				} catch (RowsExceededException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			workbook.write();
			try {
				workbook.close();
			} catch (WriteException e) {
				
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static WritableCellFormat setCellFormat() {  
        WritableCellFormat wcf = null;  
        try {  
            WritableFont wf = new WritableFont(WritableFont.TIMES, 10,  
                    WritableFont.NO_BOLD, false);   
            wcf = new WritableCellFormat(wf);  
            wcf.setAlignment(Alignment.CENTRE);  
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);  
            wcf.setWrap(true);  
        } catch (WriteException e) {  
            e.printStackTrace();  
        }  
        return wcf;  
    }  
}