#! /bin/bash
#  tomcat的access log 文件名为localhost_access_log.yyyy-xx-zz.txt 使用localhost_access_log.*.txt 当前目录所有文件
#  使用awk '{print $1}' 得到每一行的ip数据
#  sort 相同ip排在一起
#  uniq -c 合并重复的行
#  sort -nr 倒叙排序
#  head -n 10 取出前十
awk '{print $1}' localhost_access_log.*.txt | sort|uniq -c|sort -nr|head -n 10
