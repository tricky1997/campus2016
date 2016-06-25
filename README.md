#Campus 2016
##Java 部分
####开发环境：
* Windows 7 64-bits
* IntelliJ IDEA 15.0.2

####一、统计一个Java文件的有效行数。
**解题思路：**<br />
  有效行主要不包括：<br />

1. 空行
2. 单行注释
3. 多行注释（程序中添加了这部分的支持）

  [主程序](./Java/EffectiveLines.java)以行为单位读取自身.java文件，
并分别用上述三种非有效行的正则表达式去匹配每一行，其中多行注释包括起始行和结束行的正则表达式，
通过这些正则表达式确定每一行是否为有效行，最后输出其数目。

####二、分析从今天开始过去30天时间里，中国人民银行公布的人民币汇率中间价，得到人民币对美元、欧元、港币的汇率，形成excel文件输出。
**解题思路：**<br />
  首先进入[中国人民银行官网](http://www.pbc.gov.cn/zhengcehuobisi/125207/125217/125925/17105/index1.html)，观察到其显示的每一天汇率数据的URL是根据时间从最近
的日期开始排列，且分页显示，而不同的页的URL正好是按照数字编号的，即主页是
index1.html，第二页是index2.html，后面的页以此类推，因此可以先从这个页面爬取每
一天的汇率数据公示链接，然后再对链接页面上的数据进行爬取，最后输出到Excel文件中，
主要包括如下步骤：<br />

1. 通过Jsoup爬取最近30天的汇率信息公布URL；URL在页面的font.newslist style下面
的a标签的href 属性里面
2. 通过Jsoup分别爬取这30天的汇率公布信息，并通过正则表达式提取出不同的货币的汇
率值；公告信息放在#zoom内的p标签里面，提取出公告后再匹配每一项汇率，然后从
汇率项中提取出汇率浮点数
3. 利用Apache POI库将爬取到的汇率按照日期依次写入到Excel文件中，文件名为“当前
日期-最近30天汇率信息.xls”

[主程序](./Java/ExchangeRate.java)用到了一个[专门存储汇率数据和URL等信息的类](./Java/ExchangeRateBean.java)，
此外Apache POI库依赖于xmlbeans库

####三、根据指定项目目录下（可以认为是java源文件目录）中，统计被import最多的类，前十个是什么。
**解题思路：**<br />
  [主程序](./Java/CountMostImport.java)的执行步骤是：<br />

1. 首先，DFS遍历目录下的所有子目录和文件
2. 用正则表达式(.*\\.java$)匹配目录和子目录下的Java文件，并用正则表达式
("^(import)\\s[a-zA-Z0-9.]+;\\s*$") 从每个Java文件中筛选出import 的类，并放入
HashMap中，记录其出现次数，在解析每一个Java文件时，一旦匹配到Class/Interface/
Abstract Class("(class|interface|abstract)")就说明后面不可能有import语句了，停止
对该Java 文件的解析
3. 完成类解析后，再对HashMap中的类按照出现次数排序，输出import前十的类

####后记
由于程序工程包含了一些额外的类库，占用空间较大，因此压缩工程文件，文件见Java/Projects.zip

##Linux 部分
####开发环境：
* Debian 7

####一、如何统计所有日志文件（tomcat的access log）中，列出访问次数最多的10个IP，以及对应的次数。
**解题思路：**<br />
  access.log文件中每一条数据分为多个字段，每个字段用空格隔开，因此可以通过awk将IP字段对应的列提取出来，并统计每个IP出现的次数，再利用sort对出现次数排序，同时利用head筛
选出访问数最多的10个IP，[脚本程序](./Linux/Top10Ip.sh)见Linux文件夹下的Top10Ip.sh（程序默认IP在access.log文件中每一条数据的第一列）

####二、linux下如何从本地目录dir1拷贝一个文件到目标机l-test.dev.cn1的/tmp目录下？请写出具体语句。
**解题思路：**<br />
  通过scp，命令格式类似于cp，[脚本程序](./Linux/linux2.sh)见Linux文件夹下的linux2.sh，脚本运行后需要输入目标机UserName对应的密码