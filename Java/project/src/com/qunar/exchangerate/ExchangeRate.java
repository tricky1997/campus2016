package com.qunar.exchangerate;


import jxl.CellView;
import jxl.Workbook;
import jxl.write.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static javafx.scene.input.KeyCode.G;
import static javafx.scene.input.KeyCode.R;


/**
 * Created by Charmy on 2016/6/30.
 * 使用Jsoup抓取网络数据
 * */
public class ExchangeRate {
    public static void main(String[] args){
        String url = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action";
        Crawler crawler=new Crawler();
        ArrayList<ResultData> list=crawler.getMiddleRate(url);
        GenerateExecel generateExecel=new GenerateExecel();
        generateExecel.wirteExcel(list,"exchangeRate.xls");
    }
}

