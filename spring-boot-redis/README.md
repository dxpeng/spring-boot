# spring-boot-redis

#### 依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <exclusions>
        <exclusion>
            <groupId>io.lettuce</groupId>
            <artifactId>lettuce-core</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>
```

#### 配置
```
#redis
spring.redis.database=0
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.password=
spring.redis.jedis.pool.max-active=8
spring.redis.jedis.pool.max-idle=8
spring.redis.jedis.pool.min-idle=0
spring.redis.jedis.pool.max-wait=-1ms
```

#### 实体类
```
public class User implements Serializable {
    private Integer id;
    private String name;
    private String address;
    // getter setter toString...
```

#### jedis
```
@RestController
public class UserController {
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/user")
    public void getUser() {
        ValueOperations<String, String> ops1 = stringRedisTemplate.opsForValue();
        // 存字符串
        ops1.set("kname", "张三丰");
        System.out.println(ops1.get("kname"));
        // 存对象
        User user = new User();
        user.setId(1);
        user.setName("洪七公");
        user.setAddress("射雕英雄传");
        ValueOperations ops2 = redisTemplate.opsForValue();
        ops2.set("user1", user);
        System.out.println(ops2.get("user1"));
    }
}
```