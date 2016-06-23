#！/bin/sh

#log目录下执行命令

awk '{print $1}' localhost_access_log.*.txt| sort|uniq -c|sort -nr|head -n 10