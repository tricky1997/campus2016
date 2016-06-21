#!/bin/bash
#统计所有日志文件access_log中访问次数最多的１０个ＩＰ，以及对应的次数
# Author:ZhangMiaoSen

file=*access_log*.txt

if [ ! -e $file ]
  then 
    echo " The files are not exit "

else 
  awk '{print $1}' $file | sort | uniq -c | sort -nr | head -n 10

fi                            
