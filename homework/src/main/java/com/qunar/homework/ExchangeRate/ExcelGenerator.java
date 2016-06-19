package com.qunar.homework.ExchangeRate;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.List;

/**
 * 根据日期列表和汇率列表生成相应的excel文档
 * Created by zhaocai-luo on 2016/6/15.
 */
public class ExcelGenerator {
    /**
     * 汇率文档生成器
     * @param currencyName  外汇货币名称
     * @param currencyList  汇率中间值列表
     * @param timeList      查询的日期列表
     * @throws Exception
     */
    public void createExcel(String currencyName, List<String> currencyList, List<String> timeList) throws Exception {
        // 根据货币名称和时间生成相应的文档名称
        String basePath = System.getProperty("user.dir") + "./res/";
        File excelFile = new File(basePath + currencyName + "汇率信息" + timeList.get(0) + ".xls");

        // 获取excel文档写对象
        WritableWorkbook workbook = Workbook.createWorkbook(excelFile);
        WritableSheet sheet = workbook.createSheet(currencyName, 0);

        // 表头信息
        Label label = new Label(0, 0, "货币种类");
        sheet.addCell(label);
        label = new Label(1, 0, "日期");
        sheet.addCell(label);
        label = new Label(2, 0, "汇率中间值");
        sheet.addCell(label);

        int num = 1;
        for (int i = 0; i < currencyList.size(); i++) {
            String dateStr = timeList.get(i);
            String currency = currencyList.get(i);

            // 第一列为外汇货币名称
            Label currencyNameLabel = new Label(0, num, currencyName);
            sheet.addCell(currencyNameLabel);
            // 第二列为日期
            Label dateLabel = new Label(1, num, dateStr);
            sheet.addCell(dateLabel);
            // 第三列为汇率中间值
            Label currencyLabel = new Label(2, num, currency);
            sheet.addCell(currencyLabel);

            num++;
        }
        workbook.write();
        workbook.close();
    }
}
