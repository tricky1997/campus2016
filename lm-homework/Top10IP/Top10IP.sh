#!/bin/sh
#tomcat的access log 文件名为localhost_access_log.*.txt
#awk '{print $1}' 获取 IP地址
#sort 按IP地址排序
#uniq -c 合并计数
#sort -rn 倒序排序
#head -n 10 取前十
awk '{print $1}' localhost_access_log.*.txt |sort|uniq -c|sort -rn|head -n 10