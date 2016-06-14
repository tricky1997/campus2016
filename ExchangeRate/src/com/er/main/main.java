package com.er.main;

import com.er.entity.ERDocument;
import com.er.entity.RateData;
import com.er.entity.SearchRule;
import com.er.service.IExchangeRateService;
import com.er.service.impl.ExchangeRateServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by lenovo on 2016/6/7.
 */
public class main {

    public static void main(String[] args) {

        IExchangeRateService mExchangeRateService = new ExchangeRateServiceImpl();

        SearchRule rule = new SearchRule();
        rule.setmMethod(SearchRule.METHOD_POST);
        rule.setmTimeOut(10000);
        //rule.setmUrl("http://hl.anseo.cn/");
        rule.setmUrl("http://www.chinamoney.com.cn/fe-c/historyParity.do");

        ERDocument doc = mExchangeRateService.genDocument(rule);
        List<RateData> rateDataArr = mExchangeRateService.resolveRateDate(doc);
        mExchangeRateService.saveAsExcel(rateDataArr,"rateDate.xls");
    }

}
