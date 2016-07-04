package org.qunar.exchangerate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jxl.*;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Created by zhang ruixiong on 2016/6/30 0003.
 */
public class DataHandler {
    private List<Double[]> values = null;
    private List<String> dates = null;
    private final static String mark1="<tr class=\"first\" onMouseover='this.style.backgroundColor=\"#fffcbf\"' onMouseout='this.style.backgroundColor=\"#eff6fe\"'>";
    private final static String mark2="<td width=\"8%\" align=\"center\"  >";
    public static DataHandler getInstance(String html){
        DataHandler dh = new DataHandler();
        dh.format(html);
        return dh;
    }
    private DataHandler(){
        values = new ArrayList<Double[]>();
        dates = new ArrayList<String>();
    }
    private void format(String html){
        String subHtml = html;
        int index=0;
        while ((index=subHtml.indexOf(mark1))!=-1){
            subHtml = subHtml.substring(index+mark1.length());
            int startIndex = subHtml.indexOf(mark2)+mark2.length();
            int endIndex =subHtml.indexOf("&nbsp",startIndex);
            String date = subHtml.substring(startIndex,endIndex).trim();
            dates.add(date);
            subHtml = subHtml.substring(endIndex+"&nbsp".length());
            Double[] oneValue = new Double[3];
            for (int i=0;i<4;i++){
                startIndex = subHtml.indexOf(mark2)+mark2.length();
                endIndex =subHtml.indexOf("&nbsp");
                String value = subHtml.substring(startIndex,endIndex).trim();
                if (i<2) oneValue[i] = Double.valueOf(value);
                else if (i == 3) oneValue[i-1] = Double.valueOf(value);
                subHtml = subHtml.substring(endIndex+"&nbsp".length());
            }
            values.add(oneValue);
        }
    }
    public void output(){
        File file = new File("./ExchangeRate.xls");
        if (!file.exists()) try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        WritableWorkbook wb = null;
        try {
            WorkbookSettings wbs = new WorkbookSettings();
            wbs.setEncoding("GBK");
            wb = Workbook.createWorkbook(new FileOutputStream(file),wbs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WritableSheet ws = wb.createSheet("exchangerate",0);
        try {
            ws.addCell(new Label(1,0,"dollar"));
            ws.addCell(new Label(2,0,"euro"));
            ws.addCell(new Label(3,0,"Hong Kong dollar"));
        } catch (WriteException e) {
            e.printStackTrace();
        }
        for (int i=1;i<=dates.size();i++){
            try {
                ws.addCell(new Label(0,i,dates.get(i-1)));
                for (int j=1;j<=3;j++){
                    ws.addCell(new Number(j,i,values.get(i-1)[j-1]));
                }
            } catch (WriteException e) {
                e.printStackTrace();
            }

        }
        try {
            wb.write();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}
