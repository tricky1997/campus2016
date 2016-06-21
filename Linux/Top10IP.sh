 # 如何统计所有日志文件（tomcat的access log）中，列出访问次数最多的10个IP，以及对应的次数。（作业命名：Top10Ip）
 cat access_log.txt |awk '{print $1}'|uniq -c |sort -rn |head -10