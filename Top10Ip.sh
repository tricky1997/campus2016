#!/bin/bash
echo "visit times    IP"  > Top10Ip.txt
find ~ -name *access_log*.txt | xargs -n 1 cat | cut -d- -f1 | sort -n | uniq -c | sort -nr | head -n 10  >> Top10Ip.txt
