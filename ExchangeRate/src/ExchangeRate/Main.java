package ExchangeRate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {

	private static String excelPath = "Data.xls";      //输出的excel路径
	
	public static void main(String[] args) {
		
		Calendar c = Calendar.getInstance();
		Date endDay = c.getTime();   //获得今天的时间
		c.add(Calendar.DATE, -30);
		Date startDay = c.getTime();   //获得30天前的日期
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //格式化
		// 起始时间
		String startDayString = sdf.format(startDay);
		// 结束时间
		String endDayString = sdf.format(endDay);
		// 生成URL，此链接可直接得到网站导出的excel
		String url = "http://www.safe.gov.cn/AppStructured/view/project_exportRMBExcel.action?projectBean.startDate="
				+ startDayString + "&projectBean.endDate=" + endDayString;

		new GetHtmlInfo().GetHtmlInfoToExcel(url,excelPath);    //获取汇率表并输出到excel
    

	}

}
