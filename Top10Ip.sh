#!/bin/bash

#cat $1 | while read line
#do
#    echo ${line%%-*}
#done

awk '{
        a[$1]+=1;
     }END{
        for(i in a){
            print a[i]" " i;
        }
     }' $1 |sort -g -r|head -10
