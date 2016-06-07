package com.qunar.training.ExchangeRate;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.List;

/**
 * 通过JXL输出结果
 * Created by shining.cui on 2016/6/6.
 */
public class ExcelCreator {
    /**
     * 通过JXL输出对应货币的汇率信息XLS文件
     * @param currencyName 货币名称，也是文件名称
     * @param currencyList 汇率信息，包含该日期与当日汇率
     * @throws Exception
     */
    public void createExcel(String currencyName, List<List<String>> currencyList) throws Exception {
        WritableWorkbook workbook = Workbook.createWorkbook(new File(currencyName + "汇率信息" + TimeUtil.getNowDateStr() + ".xls"));
        WritableSheet sheet = workbook.createSheet(currencyName, 0);
        Label label = new Label(0, 0, currencyName);
        sheet.addCell(label);
        int i = 0;
        for (List<String> list : currencyList) {
            String dateStr = list.get(0);
            String currency = list.get(1);
            Label dateLabel = new Label(1, i, dateStr);
            Label currencyLabel = new Label(2, i++, currency);
            sheet.addCell(dateLabel);
            sheet.addCell(currencyLabel);
        }
        workbook.write();
        workbook.close();
    }
}
