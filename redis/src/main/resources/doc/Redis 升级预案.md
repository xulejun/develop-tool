# 1. 众包
## 1.1 所涉及的项目
### 众包 service（octopus-datainput-service）
- **代码变动**
    - 新建分支 redis
    - JedisPools
    - JedisInst
    - config.properties
    - Redis.properties
### 数据解析（octopus-ocr-dataparser）
- **代码变动**
    - 新建分支 redis
    - JedisPools
    - config.properties
    - Redis.properties
### 众包 portal（octopus-datainput-portal）
- **代码变动**
    - JedisPools
    - config_prod.properties
    - jedis 版本由 2.2.0 升级至 3.8.0

## 1.2 升级预案
*考虑到这三个应用使用的都是同一个 redis 实例（10.58.119.43:11222），采取应用并行发布，降低影响面；与此同时，兼顾数据准确性和影响面，尽量选择晚上发布*
### 发布流程
*注意：分支合并*
1. 应用任务中断停止
2. 登录 Redis 客户端，执行加密相关命令并持久化到配置文件
3. 三个应用并行发布（做好提前审批）
4. 调用应用相关的配置接口，检查 redis 连接是否正常
5. 查看应用的后台日志，检查是否异常报错
6. 还可通过做任务再次确认，如一切正常，升级完成

### 发布异常
1. 登录 Redis 客户端，取消加密
2. 三个应用同时回滚
3. 确定问题应用，查看应用相关的日志，必要时，登录对应的服务器查看
4. 排查问题，根据日志进行跟踪解决
5. 修复完成后，重新执行发布流程

# 2. 爬虫
## 2.1 所涉及的项目
*统一在 fat 分支进行开发测试*
### enteroctopus-server
- **代码变动**
    - 新增测试类，便于升级后确定redis连接是否正常（RedisTestServlet）
### hotel-server
- 代码未变动，在服务器上修改配置文件
### common-business
- **代码变动**
    - JedisInst
    - JedisPools
### octopus-portal
- 代码未变动，在服务器上修改配置文件
## 2.2 升级预案
*由于爬虫整个应用较为庞大，涉及到 13台redis实例（1job、8tasks、4hotel）考虑数据准确性和影响面，选择晚上发布，并在此期间任务中断*
### 发布流程
**任务中断》》发布应用》》修改配置》》Redis 加密》》应用重启**

*注意：分支合并*
1. 应用任务中断停止
2. 由于配置文件存在服务器上，且升级加密代码中已做兼容，则先发布应用
3. 登录 13 台Redis实例客户端，执行加密相关命令并持久化到配置文件
4. 修改服务器上的配置文件（/opt/ctrip/octopus/Redis.properties、hotelRedis.properties），修改之前做备份
   - 10.61.55.58
   - 10.61.55.59
   - 10.61.55.60
   - 10.60.120.100（portal）
5. 应用重启
6. 调用 Redis 连接测试接口，检查是否正常
7. 查看应用的后台日志，检查是否异常报错
### 升级异常
**修改配置》》Redis 取消加密》》应用重启**
1. 登录 13 台Redis实例客户端，取消加密
2. 配置文件回滚
3. 排查问题，根据日志进行跟踪解决
4. 修复完成后，重新执行发布流程

*由于 portal 没有代码变更，则只需要在 Redis 加密后，修改服务器上的配置文件，再进行重启即可*

# 3. 所涉及到的命令
## 3.1 Redis 无需重启修改密码持久化到配置文件
```shell
config set requirepass 123456
auth 123456
config rewrite
config get requirepass
```
## 3.2 配置文件备份 / 重命名
```shell
mv Redis.properties Redis.properties.back1
mv Redis.properties.back Redis.properties
```
## 3.3 curl 带 header 请求验证
```shell
curl localhost:8080/redis/auth -X GET  -H "Authorization: Basic YWRtaW4uZGF0YWlucHV0OmFjODA2MjYwZThiZGViZmU0MTMyNTUxZTQwZjc1ZjRl"
```
