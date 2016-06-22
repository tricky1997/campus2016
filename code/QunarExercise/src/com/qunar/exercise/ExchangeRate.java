package com.qunar.exercise;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


class parityData{
	private Calendar cal;
	private String usd;
	private String eur;
	private String hkd;
	public Calendar getCal() {
		return cal;
	}
	public void setCal(Calendar cal) {
		this.cal = cal;
	}
	public String getUsd() {
		return usd;
	}
	public void setUsd(String usd) {
		this.usd = usd;
	}
	public String getEur() {
		return eur;
	}
	public void setEur(String eur) {
		this.eur = eur;
	}
	public String getHkd() {
		return hkd;
	}
	public void setHkd(String hkd) {
		this.hkd = hkd;
	}
	@Override
	public String toString() {
		return "parityData [时间=" + cal+ ", 美元=" + usd + ",欧元=" + eur + ", 港币=" + hkd + "]";
	}
}

/**
 * 问题：分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，得到人民币对美元、欧元、港币的汇率，形成excel文件输出。
 *
 * 数据来源:http://data.bank.hexun.com/ll/rmbhl.aspx/rmbhl.aspx
 * Created by lipingyu.
 */
public class ExchangeRate {
	private static final String PARITYTIME = "日期";
	private static final String USDPARITY = "美元";
	private static final String EURPARITY = "欧元";
	private static final String HKDPARITY = "港币";
	private static final int recordSum = 30;
	private static int timeIndex;
	private static int usdIndex;
	private static int eurIndex;
	private static int hkdIndex;
	
	private static List parityList = new ArrayList<parityData>();

	/**
	 * @param args 
	 */
	
	//将list中的数据导入到excel中
	public void exportExcel(List<Object> listContent,String fileStr){
		//创建表头数组
		String[] title = {PARITYTIME,USDPARITY,EURPARITY,HKDPARITY};
		
		//创建Excel文件
		File file = new File(fileStr+"RMBParityDatas.xls");
		try {
			file.createNewFile();
			//创建工作薄
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			//创建sheet
			WritableSheet sheet1 = workbook.createSheet("RMBParity", 0);
			Label label = null;
			//第一行设置列名
			for(int i=0;i<title.length;i++){
				label = new Label(i,0,title[i]);
				sheet1.addCell(label);
			}
			//追加数据
			for(int i=1;i<listContent.size();i++){
				parityData obj = (parityData)listContent.get(i);
				label = new Label(0,i,new SimpleDateFormat("yyyy-MM-dd").format(obj.getCal().getTime()));
				sheet1.addCell(label);
				label = new Label(1,i,obj.getUsd());
				sheet1.addCell(label);
				label = new Label(2,i,obj.getEur());
				sheet1.addCell(label);
				label = new Label(3,i,obj.getHkd());
				sheet1.addCell(label);
			}
			//写入数据
			workbook.write();
			workbook.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }  
	
	//获得时间节点
    protected static Calendar getDate(){
   
        //设置成正确的时区
    	System.setProperty("user.timezone","GMT+8"); 
    	Calendar calendar = Calendar.getInstance();
        //获得30天以前的日期
    	calendar.add(Calendar.DATE, -30); 
        return calendar;
        
    }
    
    //将字符串转换成Date类型
    static public Date parseDate(String s) throws ParseException{
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(s);
    }
	
	protected void getData(Document doc,int nums){
		
			Elements divs = doc.select("table.bankTable").select("tr");
			Elements ths = divs.get(0).select("th");
			for(int i=0; i<ths.size(); i++){
				String typeName = ths.get(i).text();
				if(typeName.contains(PARITYTIME)){
					timeIndex = i;
				}else if(typeName.contains(USDPARITY)){
					usdIndex = i;
				}else if(typeName.contains(EURPARITY)){
					eurIndex = i;
				}else if(typeName.contains(HKDPARITY)){
					hkdIndex = i;
				}
			}
			int num = ((divs.size()-1)>nums)?nums:(divs.size()-1);
			for(int i=1;i<=num;i++){
				//获取每一页
				parityData pdata = new parityData();
				Elements tds = divs.get(i).select("td");
				for(int j=0;j<tds.size()-1;j++){
					if(j == timeIndex){
						Calendar calendar = Calendar.getInstance();
						try {
							calendar.setTime(parseDate(tds.get(j).text()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						pdata.setCal(calendar);
					}else if(j == usdIndex){
						pdata.setUsd(tds.get(j).text());
					}else if(j == eurIndex){
						pdata.setEur(tds.get(j).text());
					}else if(j == hkdIndex){
						pdata.setHkd(tds.get(j).text());
					}
					
				}
				parityList.add(pdata);	
			}
	}
	
	
	public Document getDocument(String url){
		try{
			return Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36").get();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void getParity(){
		int index = 1;
		while( parityList.size()< recordSum){
			Document doc = this.getDocument("http://data.bank.hexun.com/ll/rmbhl.aspx/rmbhl.aspx"+"?page="+(index++));
			this.getData(doc,recordSum-parityList.size());
		}
		
		//得到30天之前的日期,并转换成字符串，
		Calendar lastCal = getDate();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    	String dateStr1 = format1.format(lastCal.getTime());
		
		//移除list中多余的元素
		for(int i=parityList.size()-1; i>=0;i--){
			parityData data =(parityData) parityList.get(i);
			
			//将对象data的时间转换成字符串，若对象data的时间在lastCal日期之前，则移除。
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
	    	String dateStr2 = format2.format(data.getCal().getTime());
	    	if(dateStr1.compareTo(dateStr2)>0){
	    		parityList.remove(i);
	    	}
		}
        this.exportExcel(parityList,".\\\\result\\\\");
	}
	
	public static void main(String[] args) throws IOException {

		ExchangeRate ER = new ExchangeRate();
		ER.getParity();
	}
}
