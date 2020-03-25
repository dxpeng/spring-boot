# spring-boot
spring boot 开发技术整理

#### spring-boot项目启动方式
```
1. mvn spring-boot:run
2. 启动类直接运行主方法
3. 打包jar
	<packaging>jar</packaging>
	mvn clean package
	java -jar xxx.jar
4. 打包war,发布tomcat运行
	<packaging>war</packaging>
	mvn clean package
	将war包放入apache-tomcat-8.5.50\webapps\目录下
	启动tomcat服务即可访问
```

#### 自定义启动banner
```
只需在resources目录下创建一个banner.txt文件即可
在线生成banner字体
www.network-science.de/ascii/
```

#### 多环境配置
```
application-dev.properties
application-stg.properties
application-prd.properties

application.properties下指定使用配置文件：
spring.profiles.active=dev

将配置文件的自定义数据注入Bean中：
可通过@Value注解
可通过@ConfigurationProperties(prefix="user即对象名")
```

#### thymeleaf模板引擎使用
```
引入依赖
spring-boot-starter-thymeleaf

控制层返回ModelAndView
@Controller
public class UserController{
	@GetMapping("/users")
	public ModeAndView getUsers(){
		List<User> users = new ArrayList<>();
		//向users添加数据...
		
		ModeAndView mv = new ModeAndView();
		//usersObj为前端接收数据的对象名 ${usersObj}
		mv.addObject("usersObj",users);
		//设置视图模板名称，在resources/templates/目录下创建users.html
		mv.setViewName("users");
		return mv;
	}
}
```

#### 全局异常处理
```
@ControllerAdvice
public class CustomExceptionHandler{
	//此注解表示该方法可处理所有类型的异常
	@Exception(Exception.class)
	public void globalException(Exception e,HttpServletResponse res) throws Exception{
		//自定义需要返回的信息
	}
}
```

#### 常用的配置
1. tomcat配置
```
server.port=9527
server.error.path=/error
server.servlet.session.timeout=30m
server.servlet.context-path=/proname
server.tomcat.uri-encoding=utf-8
server.tomcat.max-threads=200
server.tomcat.basedir=/home/username/tmp
```

	
