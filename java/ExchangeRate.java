import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by cjq on 2016/6/26.
 */

public class ExchangeRate {
    public Document getDocument(String url) {
        try {
            return Jsoup.connect(url).post();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("aa");
        return null;
    }

    //在不足2位数的月份和日期前面加0，如2月1日转换为02-01；
    public String dateFormate(int a){
        if (a < 10) {
            return "0" + a;
        }
        return a + "";
    }

    public String twoMonthAgo(int year, int month, int day) {
        String date = null;
        if (month < 3) {
            month = (month - 2) % 12;
            year--;
        } else {
            month -= 2;
        }
        date = year + "-" + dateFormate(month) + "-" + dateFormate(day);
        return date;
    }

    //获取中间价数据，
    public List<String[]> getHtmlData() {
        List<String[]> DataList = new ArrayList();
        String url = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action?";

        String startDate = null;
        String endDate = null;
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        endDate = year + "-" + dateFormate(month) + "-" + dateFormate(day);
        startDate = twoMonthAgo(year, month, day);
        url = url + "projectBean.startDate=" + startDate + "&projectBean.endDate=" + endDate;
        System.out.println(url);


        ExchangeRate et = new ExchangeRate();
        Document document = et.getDocument(url);
//        Document document = et.getDocument("http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/index.html");
        Elements element1 = document.getElementsByClass("first");
        Elements element2 = null;

        for (int i = 0; i < 30; i++) {
            String[] parityRateOneDay = new String[4];
            element2 = element1.get(i).select("td");
            parityRateOneDay[0] = element2.get(0).text();
            parityRateOneDay[1] = element2.get(1).text();
            parityRateOneDay[2] = element2.get(2).text();
            parityRateOneDay[3] = element2.get(4).text();
            DataList.add(parityRateOneDay);
        }

        return DataList;
    }

    public void generateExcel(File file) {
        List<String[]> DataList = getHtmlData();
        try{
            WritableWorkbook wwb = Workbook.createWorkbook(file);
            //创建工作表 指定名称和位置
            WritableSheet ws = wwb.createSheet("汇率中间价", 0);
            //向表中添加Label对象
            ws.addCell(new Label(0, 0, "日期"));
            ws.addCell(new Label(1, 0, "美元"));
            ws.addCell(new Label(2, 0, "欧元"));
            ws.addCell(new Label(3, 0, "港币"));

            for (int i = 0; i < 30; i++) {
                ws.addCell(new Label(0, i+1, DataList.get(i)[0]));
                ws.addCell(new Label(1, i+1, DataList.get(i)[1]));
                ws.addCell(new Label(2, i+1, DataList.get(i)[2]));
                ws.addCell(new Label(3, i+1, DataList.get(i)[3]));
            }

            wwb.write();
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File file = new File("./汇率中间价.xls");
        ExchangeRate er = new ExchangeRate();
        er.generateExcel(file);
    }
}
