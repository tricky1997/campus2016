package com.qunar.homework.ExchangeRate;

import java.util.List;

/**
 * 作业二：
 * 分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，得到人民币对美元、欧元、港币的汇率，形成excel文件输出。
 * 汇率数据找相关的数据源，自己爬数据分析。（作业命名：ExchangeRate）
 *
 * 代码思路：
 * 1、获取从今天起往前的30天的日期
 * 2、模拟http的Get请求查询招商银行的网页，获取该日期时间段内的汇率的历史数据信息
 * 3、分析请求响应网页的源代码，提取汇率的中间价信息
 * 4、通过JXL工具，生成excel文档
 *
 * 外汇信息数据从招商银行的网站：http://fx.cmbchina.com/hq/History.aspx 中抓取
 *
 * Created by zhaocai-luo on 2016/6/15.
 */
public class Main {
     public static void main(String[] args) {
         // 1、获取30天的历史日期列表
         TimeUtil timeUtil = new TimeUtil();
         List<String> timeList = timeUtil.getTimeList();

         // 2、提取三种外汇的汇率信息
         ExchangerRater exchangerRater = new ExchangerRater();
         List<String> EURCurrenyList = exchangerRater.getEURCurreny(timeList);
         List<String> HKDCurrenyList = exchangerRater.getHKDCurreny(timeList);
         List<String> USDCurrenyList = exchangerRater.getUSDCurreny(timeList);

         // 3、生成相应的excel文档
         ExcelGenerator excelGenerator = new ExcelGenerator();
         try {
             excelGenerator.createExcel("欧元", EURCurrenyList, timeList);
             excelGenerator.createExcel("美元", USDCurrenyList, timeList);
             excelGenerator.createExcel("港币", HKDCurrenyList, timeList);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
}
