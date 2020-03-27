# spring-boot-JdbcTemplate
#### 依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<!--mysql驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
<!--数据库连接池-->
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
```
#### 实体类
```
public class User {
    private Integer id;
    private String name;
    private String address;
    getter/setter/toString...
}
```
#### 数据库操作层
```
@Repository
public class UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //增
    public int addUser(User user) {
        int result = jdbcTemplate.update("INSERT INTO user(name,address) VALUES (?,?)", user.getName(), user.getAddress());
        return result;
    }
    //删
    public int deleteUserById(Integer id) {
        int result = jdbcTemplate.update("DELETE FROM user WHERE id=?", id);
        return result;
    }
    //改
    public int updateUserById(User user) {
        int result = jdbcTemplate.update("UPDATE user SET name=?,address=? WHERE id=?", user.getName(), user.getAddress(), user.getId());
        return result;
    }
    // 查单个
    public User getUserById(Integer id) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM user WHERE id=?", new BeanPropertyRowMapper<>(User.class), id);
        return user;
    }
    //查所有
    public List<User> getAllUsers() {
        List<User> users = jdbcTemplate.query("SELECT * FROM user", new BeanPropertyRowMapper<>(User.class));
        return users;
    }
}
```
#### 服务层
```
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    //增
    public int addUser(User user) {
        return userDao.addUser(user);
    }
    //删
    public int deleteUserById(Integer id) {
        return userDao.deleteUserById(id);
    }
    //改
    public int updateUser(User user) {
        return userDao.updateUserById(user);
    }
    //查单个
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }
    //查所有
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
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
        // @RequestHeader(value = "Authentication") String token 接受请求头数据
        // @RequestHeader(value = "request-id",defaultValue = "123456") String requestId 可设置默认值
        System.out.println(user);
        userService.addUser(user);
        //开发中根据业务自定义返回JSON数据格式
        return 1;
    }

    @DeleteMapping("/del/{id}")
    public int delUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return 1;
    }

    @PutMapping("/update")
    public int updateUserById(@RequestBody User user) {
        userService.updateUser(user);
        return 1;
    }

    @GetMapping("/list/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/lists")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
```
#### postman测试
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