package com.er.service;

import com.er.entity.ERDocument;
import com.er.entity.RateData;
import com.er.entity.SearchRule;

import java.util.List;

/**
 * Created by lenovo on 2016/6/7.
 */
public interface IExchangeRateService {

    public List<RateData> resolveRateDate(ERDocument doc);

    public ERDocument genDocument(SearchRule rule);

    public void saveAsExcel(List<RateData> rateDataArr, String filePath);

}
