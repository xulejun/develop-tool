**Redis 无需重启修改密码 & 持久化到配置文件**
```shell
config set requirepass Octopus123@
auth Octopus123@
config rewrite
config get requirepass
```
**配置文件备份 / 重命名**
```shell
mv Redis.properties Redis.properties.back1
mv Redis.properties.back Redis.properties
```
**curl 带 header 请求验证**
```shell
curl localhost:8080/redis/auth -X GET  -H "Authorization: Basic YWRtaW4uZGF0YWlucHV0OmFjODA2MjYwZThiZGViZmU0MTMyNTUxZTQwZjc1ZjRl"
```
**Redis 无需重启修改最大内存 & 持久化到配置文件**
```shell
# 1024 * 1024 * 1024 * 20 (GB)
config set maxmemory 21474836480
config rewrite
info memory
```
**Redis 关闭**
```shell
./redis-cli -p 11224 shutdown
```

**Redis 指定端口指定配置文件重启**
```shell
./redis-server  /opt/app/redis/etc/redis11224.conf --port 11224
```

**Redis 模糊删除 key**
```shell
./redis-cli -h 127.0.0.1 -p 6379 -a 123456 keys "hello*" | xargs ./redis-cli -h 127.0.0.1 -p 6379 -a 123456 del
```
