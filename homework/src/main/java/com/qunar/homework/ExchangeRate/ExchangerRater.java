package com.qunar.homework.ExchangeRate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 三种外汇的汇率信息在过去30天的提取工具
 * Created by zhaocai-luo on 2016/6/15.
 */
public class ExchangerRater {
    PageParser pageParser = new PageParser();

    final String url = "http://fx.cmbchina.com/hq/History.aspx";
    final String HKDName = "港币";
    final String EUROName = "欧元";
    final String USDName = "美元";


    public List<String> getHKDCurreny(List<String> timeList){
        return getCurreny(HKDName, timeList);
    }

    public List<String> getEURCurreny(List<String> timeList){
        return getCurreny(EUROName, timeList);
    }

    public List<String> getUSDCurreny(List<String> timeList){
        return getCurreny(USDName, timeList);
    }

    /**
     * 提取一定时间段内的汇率
     * @param name 要提取的外汇货币类型
     * @param timeList 要提取的时间日期序列
     * @return 对应时间日期的相应货币的汇率中间值列表
     */
    private List<String> getCurreny(String name, List<String> timeList){
        List<String> currencyList = new ArrayList<String>();

        // 设置参数，存放在一个map中
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("startdate", timeList.get(timeList.size()-1));
        parameters.put("enddate", timeList.get(0));
        parameters.put("nbr", name);
        parameters.put("type", "days");

        // 请求网页，并得到响应结果
        String result = HttpUtil.sendGet(url, parameters);

        // 分析网页结果，提取汇率信息
        currencyList = pageParser.parsePage(result);

        return currencyList;
    }
}
