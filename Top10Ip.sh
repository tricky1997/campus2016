#!/bin/bash
find ~ -name *access_log*.txt 2>/dev/null | xargs -n1 awk '{print $1}' | sort -n|uniq -c|sort -nr |awk 'BEGIN{print "IP\t\tvisit times"} {print $2"\t"$1}'|head -n11
