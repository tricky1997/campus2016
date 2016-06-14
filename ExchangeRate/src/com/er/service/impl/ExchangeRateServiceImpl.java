package com.er.service.impl;

import com.er.dao.IExchangeRateDao;
import com.er.dao.IExchangeRateExcelDao;
import com.er.dao.impl.ExchangeRateDaoImpl;
import com.er.dao.impl.ExchangeRateExcalDaoImpl;
import com.er.entity.ERDocument;
import com.er.entity.RateData;
import com.er.entity.SearchRule;
import com.er.service.IExchangeRateService;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/6/7.
 */
public class ExchangeRateServiceImpl implements IExchangeRateService {

    private IExchangeRateDao mExchangeRateDao = new ExchangeRateDaoImpl();

    private IExchangeRateExcelDao mExchangeRateExcelDao = new ExchangeRateExcalDaoImpl();

    private int INDEX_USD = 1;

    private int INDEX_EUR = 2;

    private int INDEX_HKD = 3;

    @Override
    public ERDocument genDocument(SearchRule rule) {
        return new ERDocument(mExchangeRateDao.genExchangeRate(rule));
    }

    @Override
    public List<RateData> resolveRateDate(ERDocument doc) {
        List<RateData> rateDateArr = new ArrayList<RateData>();

        Element element = doc.getDoc().getElementsByTag("tbody").get(2);
        Elements elements = element.select("tr");
        Element tdElement = null;

        for(int i = 1; i < elements.size(); i++) {
            RateData rate = new RateData();
            tdElement = elements.get(i);
            rate.setDate(tdElement.select("div").get(0).text());
            rate.setmHkd(Double.parseDouble(tdElement.select("td").get(INDEX_HKD).text()));
            rate.setmUsd(Double.parseDouble(tdElement.select("td").get(INDEX_USD).text()));
            rate.setmEur(Double.parseDouble(tdElement.select("td").get(INDEX_EUR).text()));
            rateDateArr.add(rate);
        }

        return rateDateArr;
//        System.out.println(elements.size());
//        System.out.println(elements.get(1));
    }

    @Override
    public void saveAsExcel(List<RateData> rateDataArr, String filePath){
        mExchangeRateExcelDao.setCellVal("RateDate",0,0,"Date");
        mExchangeRateExcelDao.setCellVal("RateDate",0,1,"USD");
        mExchangeRateExcelDao.setCellVal("RateDate",0,2,"EUR");
        mExchangeRateExcelDao.setCellVal("RateDate",0,3,"HKD");

        for(int i = 0; i < rateDataArr.size(); i++){
            RateData rateData = rateDataArr.get(i);
            mExchangeRateExcelDao.setCellVal("RateDate",i+1,0,rateData.getDate());
            //System.out.println(rateData.getDate()+" "+rateData.getmEur()+" "+rateData.getmHkd()+" "+rateData.getmUsd());
            mExchangeRateExcelDao.setCellVal("RateDate",i+1,1,rateData.getmUsd());
            mExchangeRateExcelDao.setCellVal("RateDate",i+1,2,rateData.getmEur());
            mExchangeRateExcelDao.setCellVal("RateDate",i+1,3,rateData.getmHkd());
        }

        mExchangeRateExcelDao.saveAsExcel(filePath);
    }
}
