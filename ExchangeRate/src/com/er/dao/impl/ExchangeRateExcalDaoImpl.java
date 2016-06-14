package com.er.dao.impl;

import com.er.dao.IExchangeRateExcelDao;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;

import java.io.FileOutputStream;

/**
 * Created by lenovo on 2016/6/10.
 */
public class ExchangeRateExcalDaoImpl implements IExchangeRateExcelDao {

    private HSSFWorkbook mWorkbook= new HSSFWorkbook();

    @Override
    public void setCellVal(String sheetName, int x, int y, String val){
        HSSFSheet sheet = getOrCreateSheet(mWorkbook, sheetName);
        HSSFRow row = getOrCreateRow(sheet, x);
        HSSFCell cell = row.createCell(y,Cell.CELL_TYPE_STRING);
        cell.setCellValue(val);
    }

    @Override
    public void setCellVal(String sheetName, int x, int y, double val){
        HSSFSheet sheet = getOrCreateSheet(mWorkbook, sheetName);
        HSSFRow row = getOrCreateRow(sheet, x);
        HSSFCell cell = row.createCell(y,Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(val);
    }

    @Override
    public void saveAsExcel(String filePath){
        FileOutputStream os= null;

        try {
            os = new FileOutputStream(filePath);
            mWorkbook.write(os);
            os.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                os.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private HSSFSheet getOrCreateSheet(HSSFWorkbook workbook, String sheetName){
        return workbook.getSheet(sheetName) != null?workbook.getSheet(sheetName):workbook.createSheet(sheetName);
    }

    private HSSFRow getOrCreateRow(HSSFSheet sheet, int i){
        return sheet.getRow(i) != null?sheet.getRow(i):sheet.createRow(i);
    }

}
