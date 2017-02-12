# 利用scp命令进行Linux之间负责文件
# scp命令的格式是：scp local_file remote_username@remote_ip:remote_folder
# 假如要拷贝本地的文件名为test.txt，另一台l-test.dev.cn1的用户名为root

scp /dir1/test.txt root@l-test.dev.cn1:/tmp