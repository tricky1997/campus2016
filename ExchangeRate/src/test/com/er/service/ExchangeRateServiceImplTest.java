package test.com.er.service;

import com.er.entity.ERDocument;
import com.er.entity.RateData;
import com.er.entity.SearchRule;
import com.er.service.IExchangeRateService;
import com.er.service.impl.ExchangeRateServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * Created by lenovo on 2016/6/8.
 */
public class ExchangeRateServiceImplTest {

    private IExchangeRateService mExchangeRateService = new ExchangeRateServiceImpl();

    @Test
    public void resolveRateDate(){

        SearchRule rule = new SearchRule();
        rule.setmMethod(SearchRule.METHOD_POST);
        rule.setmTimeOut(10000);
        //rule.setmUrl("http://hl.anseo.cn/");
        rule.setmUrl("http://www.chinamoney.com.cn/fe-c/historyParity.do");

        ERDocument doc = mExchangeRateService.genDocument(rule);
        List<RateData> rateDataArr = mExchangeRateService.resolveRateDate(doc);

        System.out.println(rateDataArr.size());
        for(RateData rateData:rateDataArr){
            System.out.println(rateData.getDate()+" "+rateData.getmEur()+" "+rateData.getmHkd()+" "+rateData.getmUsd());
        }
    }

}
