package com.qunar.training.ExchangeRate;

import java.util.List;

/**
 * 二、分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，得到人民币对美元、欧元、港币的汇率，形成excel文件输出。
 * 汇率数据找相关的数据源，自己爬数据分析。（作业命名：ExchangeRate）
 * <p/>
 * 本题思路：
 * 1、获取从今天开始的过去30天的日期
 * 2、模拟http的post请求查询中国银行的网页，获得对应天数的外汇信息
 * 3、分析HTML5代码，获取对应货币的当日汇率，取中间值
 * 4、通过JXL工具，将汇率信息作为xls格式输出
 * 所有的外汇信息都可以从http://srh.bankofchina.com/search/whpj/search.jsp网页中爬取
 * Created by shining.cui on 2016/6/5.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        //获得从今天开始过去30天的字符串序列
        List<String> days = TimeUtil.getThirtyDaysFromNow();
        //根据30天日期获得三种货币的30天汇率信息
        Exchanger exchanger = new Exchanger();

        List<List<String>> HKDCurrency = exchanger.getHKDCurrency(days);
        List<List<String>> USDCurrency = exchanger.getUSDCurrency(days);
        List<List<String>> EUROCurrency = exchanger.getEUROCurrency(days);
        //通过excel输出
        ExcelCreator excelCreator = new ExcelCreator();
        excelCreator.createExcel("港币", HKDCurrency);
        excelCreator.createExcel("美元", USDCurrency);
        excelCreator.createExcel("欧元", EUROCurrency);


    }
}
