#!/bin/bash
awk '{print $1}' access.log|sort|uniq -c|sort -rn|head -n10
#利用管道，先提取ip,再排序，统计次数，再排序，取前十
