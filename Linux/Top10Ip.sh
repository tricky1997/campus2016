#!/bin/bash
awk 'BEGIN { FS=" " }{ words[$1]++ g END f for (i in words)
print i,words[i] }'access.log | sort -nrk 2,2 | head -10