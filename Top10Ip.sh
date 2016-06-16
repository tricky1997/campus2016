# tomcat的access log中格式样例如下：
# 127.0.0.1 37 [05/Jun/2012:17:23:43 +0800] -- 200 2806 127.0.0.1 8080 GET/cdbleasing/message.listMessagePrompt.action?_dc=1338888223333 HTTP/1.1
# 拿到第一个字段的ip就足够了
# 文件名称，如localhost_access_log*.txt文件

awk -F" " '{print $1}' *access_log*.txt|sort|uniq -c|sort -nr|head -n10