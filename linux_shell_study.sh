#!/bin/bash
#作业命名：Top10Ip
#http://www.runoob.com/linux/linux-comm-awk.html
awk '{print $1}' /home/logs/access_log.txt | sort -n | uniq -c | sort -nr | head -n 10


#作业命名：linux2
#http://www.runoob.com/linux/linux-comm-scp.html
scp -v /dir1/local_filename  remote_username@l-test.dev.cn1:/tmp



