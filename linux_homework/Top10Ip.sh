#!/bin/sh
#修改Tomcat中/conf/server.xml的配置，使得日志命名格式为“access.yyyy-MM-dd.log”
#存放路径为/usr/local/tomcat/apache-tomcat-7.0.69/logs
#awk查看并统计访问ip次数
#sort -n -k2 -r 对第二列进行降序排列(按照数值大小比较)


awk '{a[$1]++} END {for(b in a) print b"\t"a[b]}' access.*.log |sort -n -k2 -r|head -n 10
