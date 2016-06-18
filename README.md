# Traning Program for Qunar Students 2016
##Task 1 - Count Effective Lines

本例程用于统计`target/`目录下各个Java文件的有效行数

- [x] 忽略空行
- [x] 不统计单行注释，包括//...和/* .. */标记的
- [ ] 支持多行注释

- 目录结构
[EffectiveLines/](#1)　根目录
　[out/](#1.1)　class文件
　[src/](#1.1)　 源代码
　　[./CalcCodeLines.java](#1.1.1)　 有效行数计算程序
　[target/](#1.1)　待统计的Java文件

----------

##Task 2 - Web Crawler

本例程用于抓取`国家外汇管理局`网站上的人民币汇率中间价信息，并输出到Excel表格文件中。

- [x] 实时抓取最近30天的外币汇率信息
- [x] 汇率数据导出到exchange_rate.xlsx文件中
- [ ] 同时支持`*.xls`和`*.xlsx`两种格式的导出

- 目录结构
[ExchangeRate/](#1)　根目录
　[out/](#1.1)　class文件
　[src/](#1.1)　 源代码
　　[./ExchangeRate.java](#1.1.1)　　储存汇率信息的类
　　[./GetRateByJsoup.java](#1.1.1)　爬虫类
　[libs/](#1.1)　jar包

----------

##Task 3 - Calculate the Most Imported Classes

本例程用于统计指定目录下，被import最多的类（前十个）是什么。

- [x] 不限制扫描java文件的个数
- [x] 导入次数相同，按照类名的字典顺序排列
- [ ] 支持import java.lang.*形式的导入语句

- 目录结构
[ExchangeRate/](#1)　根目录
　[out/](#1.1)　class文件
　[src/](#1.1)　 源代码
　　[./EntryComparator.java](#1.1.1)　　比较排序规则
　　[./Processor.java](#1.1.1)　　　　　 统计处理程序
　　[./TestComparator.java](#1.1.1)　　 用于测试比较器
　[target/](#1.1)　待统计的Java文件

----------

##Task 4 - 