#!/bin/bash
awk '{print $1}' acess_log* |sort |uniq -c|sort -nr|head -n 10
