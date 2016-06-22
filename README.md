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

[主程序](./Java/MainModule/src/com/qunar/dan/EffectiveLines.java)以行为单位读取自身.java文件，
并分别用上述三种非有效行的正则表达式去匹配每一行，其中多行注释包括起始和结束的正则表达式，
通过这些正则表达式确定每一行是否为有效行，最后输出其数目。

####二、



####三、


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