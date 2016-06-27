package ExchangeRate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class GetHtmlInfo {

	/**
	 *   获取汇率表并输出到excel
	 *    htmlUrl：网页链接             excelPath：输出的excel路径
	 */
	public void GetHtmlInfoToExcel(String htmlUrl, String excelPath) {
		GetAllData(htmlUrl,excelPath);    //获取所有汇率数据
		try {
			ProcessExcel(excelPath);    //从所有汇率数据中，提取美元、欧元、港元的汇率
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 *   获取所有汇率数据
	 *   htmlUrl：网页链接             excelPath：输出的excel路径
	 */
	private void GetAllData(String htmlUrl,String excelPath) {
		try {
			// 打开连接
			URLConnection con = new URL(htmlUrl).openConnection();
			// 获取输入流
			InputStream in = (InputStream) con.getContent();
			// 下面为输出文件
			FileOutputStream out = new FileOutputStream(excelPath);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *   提取美元、欧元、港元的汇率
	 *   excelPath：输出的excel路径
	 */
	private static void ProcessExcel(String excelPath) throws BiffException,
			IOException, WriteException {

		InputStream instream = new FileInputStream(excelPath);
		Workbook workBook = Workbook.getWorkbook(instream);
		// 获取第一张Sheet表
		Sheet readsheet = workBook.getSheet(0);
		// 获取Sheet表中所包含的总列数
		int rsColumns = readsheet.getColumns();
		// 获取Sheet表中所包含的总行数
		WritableWorkbook writableWorkbook = Workbook.createWorkbook(new File(
				excelPath), workBook);
		WritableSheet writeSheet = writableWorkbook.getSheet(0);

		List<Integer> removeCol = new LinkedList<>();      //存储要删除的列id

		
		for (int j = 0; j < rsColumns; j++) {     //遍历第一行的每一列
			Cell cell = readsheet.getCell(j, 0);
			String content = cell.getContents();   

			if (!content.equals("日期") && !content.equals("美元")
					&& !content.equals("欧元") && !content.equals("港元"))
				removeCol.add(j);    //获得非日期、美元、欧元、港元的列id，也是将要删除的列
		}

		int removeNum = 0;    //考虑删除一列数据，excel会自动合并表格，所以设置次变量作为偏移变量
		for (int i = 0; i < removeCol.size(); ++i)
		{
			int col = removeCol.get(i);
			writeSheet.removeColumn(col - removeNum);       //删除无效数据，从而保留日期和美元、欧元、港元的汇率
			removeNum++;
		}

		writableWorkbook.write();
		writableWorkbook.close();

        
		workBook.close();
	}
}
