import jxl.CellView;
import jxl.Workbook;
import jxl.write.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by TOSHIBA on 2016/6/14.
 */
public class ExchangeRate {

    public static SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd"); //日期字符串格式

    //从中国人民银行官方网站读取近30个工作日的人民币汇率中间价
    public static Element getExchangeTableFromNet() {
        System.out.println("开始从中国人民银行官方网站读取近30个工作日的人民币汇率中间价数据数据...");
        Connection conn = Jsoup.connect("http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action");
        Calendar now = Calendar.getInstance();
        Date endDate = now.getTime();
        String endDateStr = sim.format(endDate);
        now.add(Calendar.DAY_OF_MONTH, -50);
        //官方网站数据存在法定假日无数据，需要提前几天保证读满30行数据！！
        Date startDate = now.getTime();
        String startDateStr = sim.format(startDate);
        conn.data("projectBean.startDate", startDateStr);
        conn.data("projectBean.endDate", endDateStr);
        Document doc = null;
        try {
            doc = conn.timeout(100000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element resultTable = doc.getElementsByClass("list").get(0);
        System.out.println("从网络读取数据完毕...");
        return resultTable;
    }

    // 将分析后得到的数据写入Excel表格
    public static void writeToExcel(ExchangeRateTable rateTable) {
        System.out.println("开始将数据写入Excel文件...");
        WritableWorkbook book = null;
        File file = new File(".\\result\\ExchangeRate.xls");

        //清除旧版文件
        if (file.exists()) {
            file.delete();
        }


        try {
            CellView cellView = new CellView();
            cellView.setAutosize(true); //设置自动大小

            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
            cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            cellFormat.setWrap(true); //设置单元格中布局中

            book = Workbook.createWorkbook(file);
            WritableSheet sheet = book.createSheet("近三十天汇率数据", 0);
            // 开始写入内容
            int x = 0;
            int y = 0;
            for (String title : rateTable.titles) { //标题

                Label label = new Label(x, 0, title);
                label.setCellFormat(cellFormat);
                sheet.addCell(label);
                x++;
            }
            List<ExchangeRateTable.Line> lines = rateTable.lines;
            for (x = 0; x < 30; x++)  //数据
            {
                ExchangeRateTable.Line line = lines.get(x);
                y = 0;
                Label label = new Label(y, x + 1, sim.format(line.date).toString());
                label.setCellFormat(cellFormat);
                sheet.addCell(label);
                y++;
                for (Double rate : line.exchangRates) {
                    label = new Label(y, x + 1, rate.toString());
                    label.setCellFormat(cellFormat);
                    sheet.addCell(label);
                    y++;
                }
            }
            sheet.setColumnView(x, cellView);//根据内容自动设置列宽
            book.write();
            book.close();
        } catch (Exception e) {
            System.out.println("文件写入错误...");
            e.printStackTrace();
        }
        System.out.println("文件写入数据完毕...");
    }

    public static void main(String[] args) {
        ExchangeRateTable ert = new ExchangeRateTable(getExchangeTableFromNet());
        writeToExcel(ert);
        System.out.println("近30个工作日的人民币中间汇率价格已经保存至项目下result文件夹的ExchangeRate.xls中！");
    }

}
