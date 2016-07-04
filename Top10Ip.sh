# 统计所有日志文件（tomcat的access log）中，列出访问次数最多的10个IP，以及对应的次数

awk '{print $1}' *access_log*.txt |sort |uniq -c|sort -nr|head -n 10
