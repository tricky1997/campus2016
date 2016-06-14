package com.er.dao;

import com.er.entity.SearchRule;
import org.jsoup.nodes.Document;

/**
 * Created by lenovo on 2016/6/7.
 */
public interface IExchangeRateDao {

    public Document genExchangeRate(SearchRule rule);

}
