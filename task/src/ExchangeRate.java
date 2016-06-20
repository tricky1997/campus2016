import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * 从http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action抓取近100天汇率数据
 * 将近30天数据保存到ExchangeRate.xls
 */
public class ExchangeRate {
    public static void main(String[] args){
        Connection conn = Jsoup.connect("http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action");

        //获得查询的开始截止日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long end = System.currentTimeMillis();
        long start = end - (long)100*24*60*60*1000;
        String startTime = sdf.format(start);
        String endTime = sdf.format(end);

        //获得最近100天的数据（因为节假日不公布信息，为了保证获得100天的数据，因此抓多些）
        conn.data("projectBean.startDate", startTime);
        conn.data("projectBean.endDate", endTime);
        Document doc = null;
        try {
            doc = conn.timeout(5000).get();
        } catch (IOException e) {
            System.out.println("连接错误！");
            return;
        }
        //获取近30天数据
        Elements elements = doc.getElementsByClass("first");
        //依次保存日期、美元、欧元、港币数据
        String exchangeData[][] = new String[4][30];
        for(int i = 0; i < 30; i++){
            exchangeData[0][i] = elements.get(i).child(0).text();
            exchangeData[1][i] = elements.get(i).child(1).text();
            exchangeData[2][i] = elements.get(i).child(2).text();
            exchangeData[3][i] = elements.get(i).child(4).text();
        }


        //将数据保存到Excel文件中
        WritableWorkbook exchangeRateExcel = null;
        try {
            exchangeRateExcel = Workbook.createWorkbook(new File("ExchangeRate.xls"));
        } catch (IOException e) {
            System.out.print("写文件Excel时出错！");
            return;
        }

        //创建sheet
        WritableSheet firstSheet = exchangeRateExcel.createSheet("人民币汇率", 1);
        //设置文字水平、竖直方向居中
        WritableCellFormat centerFormat = new WritableCellFormat();
        try {
            centerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            centerFormat.setAlignment(Alignment.CENTRE);
        } catch (WriteException e) {
            System.out.print("设置格式出错！\n");
        }
        final int startRow = 5, startColumn = 5;
        firstSheet.setColumnView(startColumn, "yyyy-MM-dd".length() + 2);
        firstSheet.setColumnView(startColumn+1, 10);
        firstSheet.setColumnView(startColumn+2, 10);
        firstSheet.setColumnView(startColumn+3, 10);

        //设置表头行
        Label dateCell = new Label(startColumn,startRow,"日期", centerFormat);
        Label dollarCell = new Label(startColumn+1,startRow,"美元", centerFormat);
        Label euroCell = new Label(startColumn+2,startRow,"欧元", centerFormat);
        Label hkdollarCell = new Label(startColumn+3,startRow,"港币", centerFormat);

        //写入数据
        try {
            firstSheet.addCell(dateCell);
            firstSheet.addCell(dollarCell);
            firstSheet.addCell(euroCell);
            firstSheet.addCell(hkdollarCell);

            for(int i = 0; i < 30; i++){
                for(int j = 0; j < 4; j++){
                    firstSheet.addCell(new Label(startColumn+j, startRow+1+i, exchangeData[j][i], centerFormat));
                }
            }
            exchangeRateExcel.write();
            exchangeRateExcel.close();
        } catch (Exception e) {
            System.out.println("写入数据到Excel文件时发生错误！");
            return;
        }

    }
}
