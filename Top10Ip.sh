# tomcat��access log�и�ʽ�������£�
# 127.0.0.1 37 [05/Jun/2012:17:23:43 +0800] -- 200 2806 127.0.0.1 8080 GET/cdbleasing/message.listMessagePrompt.action?_dc=1338888223333 HTTP/1.1
# �õ���һ���ֶε�ip���㹻�ˡ�ͳ��ip����������
# �ļ����ƣ���localhost_access_log*.txt�ļ���ͨ����� *access_log*.txt

awk -F" " '{print $1}' *access_log*.txt|sort|uniq -c|sort -nr|head -n10