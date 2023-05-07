## Jedis
- Jedis 是 Redis 官方推荐使用客户端
- Jedis 的性能至少是 RedisTemplate 的3倍以上
- 面对高并发插入查询时，使用 Pipeline 会使 Jedis 性能再次提升
- Jedis 需要结合 JedisPool 使用，既可以有高性能又可以保证redis的连接可控，还可解决线程安全问题
- 在性能要求、并发操作不高的场景建议使用 RedisTemplate；在并发高，性能要求高的场景下建议使用 Jedis
## RedisTemplate
- RedisTemplate 比 Jedis 多了自动管理连接池的特性，方便与 Spring Boot 整合
- Spring Boot 2.0 以前默认客户端为 Jedis，2.x以上则换成了 Lettuce
    - Jedis 线程不安全，Lettuce 是线程安全（Jedis 池化则不存在）
    - Lettuce 底层使用的是 Netty（NIO），且支持异步， Jedis 为阻塞式IO
    - Jedis 是同步，不支持异步