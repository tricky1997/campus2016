##一、如何统计所有日志文件（tomcat的access log）中，列出访问次数最多的10个IP，以及对应的次数。（作业命名：Top10Ip）

##使用awk获取文件所有ip部分，通过sort将同一个ip放在一起，然后通过uniq 统计重复次数，然后根据sort按照数字倒叙排序显示前10个

awk '{print $1}' access.log|sort|uniq -c|sort -nr|head -n10