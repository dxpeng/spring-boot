# spring-boot-mybatis
spring-boot 整合 mybatis

#### 依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.2</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.20</version>
</dependency>
```

#### 配置
```
#mysql
spring.datasource.url=jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource

#mybatis mapper.xml
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml

```

#### 创建表
```
CREATE TABLE
IF
	NOT EXISTS `user` ( 
	`id` INT NOT NULL, 
	`name` VARCHAR ( 32 ), 
	`address` VARCHAR ( 64 ), 
	PRIMARY KEY ( `id` ) 
	) ENGINE = INNODB DEFAULT charset = utf8;
```

#### 实体类 Entity
```
public class User {
    private Integer id;
    private String name;
    private String address;
    getter/setter/toString...
}
```

#### 持久层 Mapper
```
/**
 * 数据库访问层
 * 数据增删改查
 * 可在每个类加注解 @Mapper
 * 在入口类统一扫描 @MapperScan("com.xpit.springbootmybatis.mapper")
 */
public interface UserMapper {
    int addUser(User user);
    int deleteUserById(Integer id);
    int updateUserById(User user);
    User getUserById(Integer id);
    List<User> getAllUsers();
}
```

#### mapper.xml 编写SQL
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.xpit.springbootmybatis.mapper.UserMapper">
    <!--id对应mapper接口的方法-->
    <insert id="addUser" parameterType="com.xpit.springbootmybatis.entity.User">
        INSERT INTO user(name,address) VALUES (#{name},#{address})
    </insert>
    <delete id="deleteUserById" parameterType="int">
        DELETE FROM user WHERE id=#{id}
    </delete>
    <update id="updateUserById" parameterType="com.xpit.springbootmybatis.entity.User">
        UPDATE user SET name=#{name},address=#{address} WHERE id=#{id}
    </update>
    <select id="getUserById" parameterType="int" resultType="com.xpit.springbootmybatis.entity.User">
        SELECT * FROM user WHERE id=#{id}
    </select>
    <select id="getAllUsers" resultType="com.xpit.springbootmybatis.entity.User">
        SELECT * FROM user
    </select>
</mapper>
```

#### 服务层接口 定义业务接口等
```
public interface UserService {
    int addUser(User user);
    int deleteUserById(Integer id);
    int updateUserById(User user);
    User getUserById(Integer id);
    List<User> getAllUsers();
}
```

#### 服务层实例类
```
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int deleteUserById(Integer id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public int updateUserById(User user) {
        return userMapper.updateUserById(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }
}
```

#### 控制层
```
@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public int addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping("/del/{id}")
    public int delUserById(@PathVariable Integer id) {
        return userService.deleteUserById(id);
    }

    @PutMapping("/update")
    public int updateUserById(@RequestBody User user) {
        return userService.updateUserById(user);
    }

    @GetMapping("/list/{id}")
    public User getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @GetMapping("/list")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
```

#### 测试
```
post
http://localhost:8080/v1/user/add

delete
http://localhost:8080/v1/user/del/1

put
http://localhost:8080/v1/user/update

get
http://localhost:8080/v1/user/list/1

get
http://localhost:8080/v1/user/lists
```
