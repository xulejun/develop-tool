## 磁盘相关
- 查看磁盘的占用
```shell
df -lh
```
- 查找当前目录下最大目录/文件（前 10）
```shell
du -Sh | sort -rh | head -n 10
```
- 查找 home目录下 最大的10个文件
```shell
find /home -type f -exec ls -s {} \; | sort -n | tail -n 10
```
- 查找显示大于100MiB的所有文件（*\ 为单斜杠，复制时请注意*）
```shell
find / -size +100M -exec ls -lh {} \;
```