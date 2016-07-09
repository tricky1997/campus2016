awk '{print $1}' access_120101.log sort|uniq -c|sort -rn|head -n 10

