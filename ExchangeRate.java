/**
 * 二、分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，
 * 得到人民币对美元、欧元、港币的汇率，形成excel文件输 出。汇率数据找相关的数据源，
 * 自己爬数据分析。
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * @author JiangHP
 */
public class ExchangeRate {
	public static void main(String[] args) {
		FileOutputStream out = null;
		URLConnection con = null;
		InputStream in = null;
		Calendar c = Calendar.getInstance();
		Date endDay= c.getTime();
		c.add(Calendar.DATE,-30);
		Date startDay = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//起始时间字符串 2015-11-03
		String startDayString = sdf.format(startDay);
		//结束时间字符串 2015-10-08
		String endDayString = sdf.format(endDay);
		//拼接URL，可以直接导出excel的网站
		String url="http://www.safe.gov.cn/AppStructured/view/project_exportRMBExcel.action?projectBean.startDate="+
				startDayString+"&projectBean.endDate="+endDayString;
		String classPath = ExchangeRate.class.getResource("/").getFile();
		try {
			//打开连接
			con = new URL(url).openConnection();
			//获取输入流
			in = (InputStream) con.getContent();
			//下面为输出文件
			out = new FileOutputStream(classPath+"ExchangeRate.xls");
			byte[] buf = new byte[1024];
			int len;
			while((len=in.read(buf))!=-1){
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
