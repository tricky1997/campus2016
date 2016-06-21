#!/bin/bash
#将本地目录dir1　拷贝一个文件到目标机１－.dev.cn1的／tmp目录下
#文件以参数形式传入
# Author:ZhangMiaoSen

read -p "please input a filename: " file
if [ ! -e $file ]
  then 
     echo "The file is not exit"
else
  scp -ri file root@1-test.dev.cn1:/temp
fi
