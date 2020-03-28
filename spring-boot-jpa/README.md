# spring-boot-jpa
spring boot 整合 jpa

#### 依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
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

#jps
spring.jpa.show-sql=true
spring.jpa.database=mysql
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
```

#### 实体类
```
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    //getter setter toString...
}
```

#### 数据持久层 Dao
```
public interface UsersDao extends JpaRepository<Users, Integer> {
    // 可自定义数据表操作接口
}
```

#### 服务层
```
@Service
public class UsersService {
    @Autowired
    private UsersDao usersDao;

    public List<Users> getAllUsers(){
        return usersDao.findAll();
    }
}
```

#### 控制层
```
@RestController
@RequestMapping("/v1/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("lists")
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }
}
```


#### 多数据源配置
```
#数据源1
spring.datasource.one.url=jdbc:mysql://localhost:3306/test1?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
spring.datasource.one.username=root
spring.datasource.one.password=root
spring.datasource.one.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.one.type=com.alibaba.druid.pool.DruidDataSource

#数据源2
spring.datasource.two.url=jdbc:mysql://localhost:3306/test2?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
spring.datasource.two.username=root
spring.datasource.two.password=root
spring.datasource.two.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.two.type=com.alibaba.druid.pool.DruidDataSource

#jps
spring.jpa.properties.show-sql=true
spring.jpa.properties.database=mysql
spring.jpa.properties.hibernate.ddl-auto=update
spring.jpa.properties.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
```

#### 数据源配置
```
@Configuration
public class DataSourceConfig{
    @Bean
    @ConfigurationProperties("spring.datasource.one")
    DataSource dsOne(){
        return DruidDataSourceBuilder.create().build();
    }
    
    @Bean
    @ConfigurationProperties("spring.datasource.two")
    DataSource dsTwo(){
        return DruidDataSourceBuilder.create().build();
    }
}
```

	
