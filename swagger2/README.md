# spring-boot
spring boot 配置 swagger2

#### 依赖
```
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger2</artifactId>
	<version>2.9.2</version>
</dependency>
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger-ui</artifactId>
	<version>2.9.2</version>
</dependency>
```

#### 配置
```
package com.xpitd.swagger2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage(""))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("swagger自定义标题")
                        .description("自定义描述信息")
                        .version("9.0")
                        .contact(new Contact("联系作者", "xpit.com", "xp@qq.com"))
                        .licenseUrl("http://xpit.com")
                        .build());
    }
}

```

#### swagger2注解的使用
```
// 1.实体类字段描述

package com.xpitd.swagger2.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class User {
    @ApiModelProperty(value = "用户ID")
    private Integer id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "用户地址")
    private String address;
	// getter setter toString...
}

```

```
// 2. 控制层注解

package com.xpitd.swagger2.controller;

import com.xpitd.swagger2.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "用户管理相关接口")
@RequestMapping("/user")
public class UserController {
    @PostMapping("/add")
    @ApiOperation("添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "张三"),
            @ApiImplicitParam(name = "address", value = "用户地址", defaultValue = "深圳", required = true)
    })
    public User addUser(String username, @RequestParam(required = true) String address) {
        User user = new User();
        user.setUsername(username);
        user.setAddress(address);
        return user;
    }

    @GetMapping("/list/{id}")
    @ApiOperation("根据id查用户")
    @ApiImplicitParam(name = "id", value = "用户id", defaultValue = "11", required = true)
    public User getUserById(@PathVariable Integer id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @PutMapping("/update")
    @ApiOperation("根据id更新用户")
    public User updateUserById(@RequestBody User user) {
        return user;
    }
}

```

#### 访问接口文档
```
启动项目后访问
http://localhost:8080/swagger-ui.html#/

使用步骤
1. 点开某个接口
2. 点击 " Try it out"
3. 点击 "Execute" 发送请求
4. 查看接口请求参数、返回数据
```

#### 接口文档访问权限问题
```
如果项目中集成了Spring Security，需要在Spring Security的配置类中重写configure方法。

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 对请求授权
                .antMatchers("/swagger-ui.html").permitAll() //允许所有人访问
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/csrf").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest() // 任何请求
                .authenticated()// 需要身份认证
                .and()
                .formLogin()
        ;
    }
}


```





