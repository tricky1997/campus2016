# Traning Program for Qunar Students 2016
###Task 1 - Count Effective Lines

本例程用于统计`target/`目录下各个Java文件的有效行数。

- [x] 忽略空行
- [x] 不统计单行注释，包括//...和/* .. */标记的
- [ ] 支持多行注释

目录结构

* EffectiveLines/　根目录
  * out/　　class文件
  * src/　　 源代码
    * ./CalcCodeLines.java　 有效行数计算程序
  * target/　待统计的Java文件

----------

###Task 2 - Web Crawler

本例程用于抓取`国家外汇管理局`网站上的人民币汇率中间价信息，并输出到Excel表格文件中。

- [x] 实时抓取最近30天的外币汇率信息
- [x] 汇率数据导出到exchange_rate.xlsx文件中
- [ ] 同时支持`*.xls`和`*.xlsx`两种格式的导出

目录结构

* ExchangeRate/　根目录
  * out/　　class文件
  * src/　 　源代码
    * ./ExchangeRate.java　　储存汇率信息的类
    * ./GetRateByJsoup.java　爬虫类
  * libs/　　jar包

----------

###Task 3 - Calculate the Most Imported Classes

本例程用于统计指定目录下，被import最多的类（前十个）是什么。

- [x] 不限制扫描java文件的个数
- [x] 导入次数相同，按照类名的字典顺序排列
- [ ] 支持import java.lang.*形式的导入语句

目录结构

* ExchangeRate/　根目录
  * out/　　class文件
  * src/　 　源代码
    * ./EntryComparator.java　　比较排序规则
    * ./Processor.java　　　　　统计处理程序
    * ./TestComparator.java　　用于测试比较器
  * target/　待统计的Java文件

----------

###Task 4 - List the Top-10 Visited IP in The Access Log

本例程用于统计Tomcat日志文件访问次数最多的 10 个 IP,以及对应的次数。

----------

###Task 5 - Copy the Local files to the Remote Machine

从本地目录 dir1 拷贝一个文件到目标机l-test.dev.cn1 的/tmp 目录下。
