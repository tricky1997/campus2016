#! /bin/bash
# scp是有Security的文件copy，基于ssh登录
# 运行 ./scpLi.sh时，参数需要指定 
# $1 本地文件的绝对路径
# $2 目标机名
# $3 目标机的绝对路径
# ./scpLi.sh /dir1/test.txt l-test.dev.cn1 /tmp/  
scp $1 $2:$3
