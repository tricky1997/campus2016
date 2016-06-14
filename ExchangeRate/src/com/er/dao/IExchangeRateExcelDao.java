package com.er.dao;

/**
 * Created by lenovo on 2016/6/10.
 */
public interface IExchangeRateExcelDao {

    public void saveAsExcel(String filePath);

    public void setCellVal(String sheetName, int x, int y, String val);

    public void setCellVal(String sheetName, int x, int y, double val);

}
