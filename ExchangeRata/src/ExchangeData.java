import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sun.rowset.internal.Row;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 主要通过Jsoup进行网页的内容的解析
 */
public class ExchangeData {
	//汇率记录列表
    private static List<Rate_Record> Rate_List;

    public ExchangeData() {
        Rate_List = new ArrayList<Rate_Record>();
    }
//根据网址，应用Jsoup进行解析，找出30天内不同种类的货币对人民的汇率
    public static Document getDataByJsoup(String url) {
        Document doc2 = null;
        SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前日期
        Calendar C_Time = Calendar.getInstance();
        //获取当前日期前30天的日期
        Calendar P_Time = Calendar.getInstance();
        P_Time.add(P_Time.DATE, -30);
        try {
			//获取一个新的连接，用get()取得和解析一个HTML
            doc2 = Jsoup.connect(url).timeout(6000)
                    .data("projectBean.startDate", Date_Format.format(P_Time.getTime()))
                    .data("projectBean.endDate", Date_Format.format(C_Time.getTime()))
                    .get();
            String title = doc2.body().toString();
        } catch (SocketTimeoutException e) {
            System.out.println("Socket连接超时");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc2;
    }
   //对Jsoup解析出的网页进行分析
    public static void Pars_HTML() {
       //最终输出的xls表格中第一行数据
        Rate_Record Zero_record = new Rate_Record("港币", "欧元", "美元", "日期");
        Rate_List.add(Zero_record);
		//待抓取的网页数据
        Document doc = ExchangeData.getDataByJsoup
                ("http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action");
        String title = doc.body().toString();
		//从网页数据可以分析，得到所有的汇率数据都处于first类下面，所有只需对class=“first”下的节点进行解析
        Elements elements = doc.getElementsByClass("first");
        for (Element element : elements) {
			//从网页数据可以发现，汇率所需的数据的时间、港币对人民币、欧元兑人民币、美元对人民币分别位于0、4、1、2子节点中。
            Rate_Record temp_record = new Rate_Record();
            temp_record.setDate(element.child(0).text());
            temp_record.setEurcny(element.child(2).text());
            temp_record.setHkdcny(element.child(4).text());
            temp_record.setUsdcny(element.child(1).text());
			//把一条记录加入汇率记录中
            Rate_List.add(temp_record);

        }
    }
    public static  void Export_Excel(String Excel_Name) {
        File Excel_File = new File(Excel_Name);
        String worksheet = "人民币对美欧港元汇率";//输出的excel文件工作表名
        WritableWorkbook workbook;
        try {
            //创建可写入的Excel工作薄,运行生成的文件在tomcat/bin下
            //workbook = Workbook.createWorkbook(new File("output.xls"))
            workbook = Workbook.createWorkbook(Excel_File);
            WritableSheet sheet = workbook.createSheet(worksheet, 0); //添加第一个工作表
            for (int i = 0; i < Rate_List.size(); i++) {
				//添加对应标签下的数据到xls对应表格中
                jxl.write.Label Date_label = new jxl.write.Label(0, i, Rate_List.get(i).getDate());  //当前日期数据
                jxl.write.Label HK_label = new jxl.write.Label(1, i, Rate_List.get(i).getHkdcny());  //港币对人民币的汇率数据
                jxl.write.Label EU_label = new jxl.write.Label(2, i, Rate_List.get(i).getEurcny());  //欧元对人民币的汇率数据
                jxl.write.Label US_label = new jxl.write.Label(3, i, Rate_List.get(i).getUsdcny());  //美元对人民币的汇率数据
                sheet.addCell(Date_label);
                sheet.addCell(HK_label);
                sheet.addCell(EU_label);
                sheet.addCell(US_label);
            }
            workbook.write();
            workbook.close();

        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}