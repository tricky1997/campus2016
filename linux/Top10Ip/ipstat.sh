#!/bin/bash

# Count the top-10 visited ip
cat localhost_access_log.2015-12-01.txt | awk -F' ' '{print $1}' \
	| sort | uniq -c | sort -nr | head -n 10
