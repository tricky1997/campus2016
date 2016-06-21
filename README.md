#Campus 2016
##Java 部分
####开发环境：
* Windows 7 64-bits
* IntelliJ IDEA 15.0.2
####一、

##Linux 部分
####开发环境：
* Debian 7
####一、如何统计所有日志文件（tomcat的access log）中，列出访问次数最多的10个IP，以及对应的次数。
解题思路：<br />
access.log文件中每一条数据分为多个字段，每个字段用空格隔开，因此可以通过awk将IP字
段对应的列提取出来，并统计每个IP出现的次数，再利用sort对出现次数排序，同时利用head筛
选出访问数最多的10个IP，脚本如下（默认IP在access.log文件中每一条数据的第一列）
[程序](./Linux/Top10Ip.sh)首先将
###二、