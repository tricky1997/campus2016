#
# tomcat的日志文件access.log的格式如下：
# 223.104.175.50 - - [14/Jan/2016:00:01:01 +0800] "POST /PersonalizedUpgrade HTTP/1.1" 403 169 "-" "Apache-HttpClient/UNAVAILABLE (java 1.4)" w-sweng2.mobi.zzbc.qihoo.net:80 176 5.000
#
# 思路：
# 1、用awk命令用'-'分割得到第一部分就是ip地址
# 2、用sort命令进行排序将相同的ip放一起
# 3、用nuiq命令去重并统计ip访问的次数
# 4、用sort命令将统计次数按降序排序
# 5、用head命令取出前10，即为最多访问的10个ip

awk -F '-' '{print $1}' ./access.log |sort |uniq -c| sort -rn| head -10
