package test.com.er.dao;

import com.er.dao.IExchangeRateDao;
import com.er.dao.impl.ExchangeRateDaoImpl;
import com.er.entity.SearchRule;
import org.jsoup.nodes.Document;
import org.junit.Test;

/**
 * Created by lenovo on 2016/6/7.
 */
public class ExchangeRateDaoTest {

    private IExchangeRateDao mExchangeRateDao = new ExchangeRateDaoImpl();

    @Test
    public void genExchangeRate(){
        SearchRule rule = new SearchRule();
        rule.setmMethod(SearchRule.METHOD_POST);
        rule.setmTimeOut(10000);
        //rule.setmUrl("http://hl.anseo.cn/");
        rule.setmUrl("http://www.chinamoney.com.cn/fe-c/historyParity.do");

        Document doc = mExchangeRateDao.genExchangeRate(rule);
        System.out.println(doc.getElementsByTag("tbody").get(2).toString());

    }

}
