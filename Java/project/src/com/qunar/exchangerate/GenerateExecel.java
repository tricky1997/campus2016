package com.qunar.exchangerate;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Charmy on 2016/6/30.
 * 使用jxl将数据写入到execl中，生成excel文件
 */
public class GenerateExecel {
    public void wirteExcel(ArrayList<ResultData> list, String fileName){
        WritableWorkbook book=null;
        try {
            book = Workbook.createWorkbook(new File(fileName));
            WritableSheet sheet = book.createSheet("人民币中间价", 1);

            //依次设置每一列的宽度为自适应
            CellView cellView = new CellView();
            cellView.setAutosize(true);
            for(int i=0;i<4;i++)
                sheet.setColumnView(i,cellView);
            sheet.setColumnView(1,cellView);

            //设置表头
            Label dateCell = new Label(0,0,"日期");
            Label dollarCell = new Label(1,0,"美元");
            Label euroCell = new Label(2,0,"欧元");
            Label hkdCell = new Label(3,0,"港币");
            sheet.addCell(dateCell);
            sheet.addCell(dollarCell);
            sheet.addCell(euroCell);
            sheet.addCell(hkdCell);
            for (int j = 0; j < list.size(); j++) {
                Label label1 = new Label(0, j+1, list.get(j).getDate());
                Label label2 = new Label(1, j+1, list.get(j).getDollar());
                Label label3 = new Label(2, j+1, list.get(j).getEuro());
                Label label4 = new Label(3, j+1, list.get(j).getHkd());
                sheet.addCell(label1);
                sheet.addCell(label2);
                sheet.addCell(label3);
                sheet.addCell(label4);
            }
            book.write();
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
