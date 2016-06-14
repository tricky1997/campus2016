package test.com.er.dao;

import com.er.dao.IExchangeRateExcelDao;
import com.er.dao.impl.ExchangeRateExcalDaoImpl;
import org.junit.Test;

/**
 * Created by lenovo on 2016/6/10.
 */
public class ExchangeRateExcalDaoTest {

    private IExchangeRateExcelDao excelDao = new ExchangeRateExcalDaoImpl();

    @Test
    public void saveAsExcel(){
    //    excelDao.saveAsExcel(null);
    }

}
