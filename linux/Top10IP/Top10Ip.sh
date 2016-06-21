#!/bin/bash
#统计访问次数最多的10个ip
cat access.log.txt | awk '{print $1}' | uniq -c | sort -rn | head -10 > result.txt
