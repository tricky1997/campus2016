package com.er.dao.impl;

import com.er.dao.IExchangeRateDao;
import com.er.entity.SearchRule;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by lenovo on 2016/6/7.
 */
public class ExchangeRateDaoImpl implements IExchangeRateDao {

    @Override
    public Document genExchangeRate(SearchRule rule) {
        Connection conn = Jsoup.connect(rule.getmUrl());
        Document doc = null;

        if(rule.getmParams().size() != 0)
            conn.data(rule.getmParams());
        conn.timeout(rule.getmTimeOut());
        //conn.userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)");
        conn.userAgent(rule.getmUserAgent());
        conn.cookie("auth", "token");

        try {
            if (rule.getmMethod() == SearchRule.METHOD_GET) {
                doc = conn.get();
            } else if(rule.getmMethod() == SearchRule.METHOD_POST){
                doc = conn.post();
            } else{

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return doc;
    }
}
