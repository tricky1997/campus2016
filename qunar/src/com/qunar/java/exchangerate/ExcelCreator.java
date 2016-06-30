package com.qunar.java.exchangerate;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: 刘能
 * Date: 16-6-28
 * Time: 上午2:12
 * 功能：Excel处理类
 */
public class ExcelCreator {

    /**
     * 功能：创建Excel，并将数据写入到Excel中
     *
     * @param map
     */
    public void createExcel(HashMap<String, ArrayList<String>> map) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheetOfDollar = wb.createSheet("美元汇率");
        XSSFSheet sheetOfEUR = wb.createSheet("欧元汇率");
        XSSFSheet sheetOfHK = wb.createSheet("港币汇率");
        XSSFRow rowOfDollar = sheetOfDollar.createRow(0);
        XSSFRow rowOfEUR = sheetOfEUR.createRow(0);
        XSSFRow rowOfHK = sheetOfHK.createRow(0);
        rowOfDollar.createCell(0).setCellValue("时间");
        rowOfDollar.createCell(1).setCellValue("汇率");
        rowOfEUR.createCell(0).setCellValue("时间");
        rowOfEUR.createCell(1).setCellValue("汇率");
        rowOfHK.createCell(0).setCellValue("时间");
        rowOfHK.createCell(1).setCellValue("汇率");


        Object[] key = map.keySet().toArray();
        Arrays.sort(key);
        for (int i = 0; i < key.length; i++) {
            ArrayList<String> list = map.get(key[i]);

            XSSFRow rowOfTDollar = sheetOfDollar.createRow(i + 1);
            XSSFCell cell0OfDollar = rowOfTDollar.createCell(0);
            XSSFCell cell1OfDollar = rowOfTDollar.createCell(1);
            cell0OfDollar.setCellValue(key[i] + "");
            cell1OfDollar.setCellValue(list.get(0));

            XSSFRow rowOfTEUR = sheetOfEUR.createRow(i + 1);
            XSSFCell cell0OfTEUR = rowOfTEUR.createCell(0);
            XSSFCell cell1OfTEUR = rowOfTEUR.createCell(1);
            cell0OfTEUR.setCellValue(key[i] + "");
            cell1OfTEUR.setCellValue(list.get(1));
            XSSFRow rowOfTHK = sheetOfHK.createRow(i + 1);
            XSSFCell cell0OfTHK = rowOfTHK.createCell(0);
            XSSFCell cell1OfTHK = rowOfTHK.createCell(1);
            cell0OfTHK.setCellValue(key[i] + "");
            cell1OfTHK.setCellValue(list.get(2));
        }
        FileOutputStream os = null;
        try {
            os = new FileOutputStream("汇率信息统计.xlsx");
            wb.write(os);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
