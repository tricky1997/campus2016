package com.qunar.test.exchangerate;

import com.qunar.java.exchangerate.ExcelCreator;
import com.qunar.java.exchangerate.ExchangeRate;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: 刘能
 * Date: 16-6-28
 * Time: 下午4:25
 * 功能：汇率计算测试类
 */
public class TestExchangeRate {

    @Test
    public void testExchangeRate() {
        ExchangeRate er = new ExchangeRate();
        String urlStr = "http://www.safe.gov.cn/AppStructured/view/project_RMBQuery.action";
        HashMap<String, ArrayList<String>> map = er.getExchangeRate(urlStr);
        ExcelCreator ec = new ExcelCreator();
        ec.createExcel(map);
    }
}
