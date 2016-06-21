#!/bin/sh
#scp 本机文件路径 远程登录用户名@远程服务器ip:目标机文件目录
#注意默认端口号
#需要正确配置ssh和sshd服务

scp /dir1/hello.txt fengqi@l-test.dev.cn1:/tmp

#若拷贝文件夹则需要加入参数 -r
#scp -r /dir1/hello fengqi@l-test.dev.cn1:/tmp
