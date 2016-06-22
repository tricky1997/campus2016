package com.qunar;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hanju on 2016/6/21.
 */
public class ExchangeRateTest {
    @Test
    public void getRateAndExport() throws Exception {
        ExchangeRate exchangeRate=new ExchangeRate();
        exchangeRate.getRateAndExport();
    }

}