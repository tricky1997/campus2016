#!/bin/bash

# Count the top-10 visited ip
cat access.log | awk -F' ' '{print $1}' \
	| sort | uniq -c | sort -nr | head -n 10