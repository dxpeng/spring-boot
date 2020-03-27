# spring-boot
spring boot 开发技术整理

#### maven仓库
```
<mirror>
		<id>nexus-aliyun</id>
		<mirrorOf>*</mirrorOf>
		<name>Nexus aliyun</name>
		<url>http://maven.aliyun.com/nexus/content/groups/public</url>
</mirror>
```

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

#### 全局数据
```
@ControllerAdvice
public class globalConfig{
	@ModelAttribute(value="keyname")
	public String funcname(){
		return "values";
	}
}

在controller中可通过方法参数中的Model获取keyname的数据
@RestController
public class user(){
	@GetMapping("/user")
	public void user(Model model){
		// model -> keyname:values
	}
}
```

#### 请求预处理
```
@ControllerAdvice
public class globalConfig{
	@InitBinder("aa")
	public void init(WebDataBinder binder){
		binder.setFieldDefaultPrefix("aa.")
	}
}

在控制层
@RestController
public class user(){
	@GetMapping("/user")
	public void user(@ModelAttribute("aa") User user){
		
	}
}
```

#### CORS跨域请求配置
```
//第一种：在请求方法上加注解
@RestController
public class UserController{
	@PostMapping("add")
	// value是支持跨域的请求域，maxAge表示探测请求的有效期，在有效期内不会发送Options探测请求。
	@CrossOrigin(value="*",maxAge=1800,allowedHeaders="*")
	public void addUser(User user){}
}

//第二种：全局配置，对所有请求都有效
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer{
	@Override
	public void addCorssMappins(CorsRegistry registry){
		registry.addMapping("/**").allowedHeaders("*").allowedMethods("*").maxAge(1800).allowedOrigins("*");
	}
}
```

#### XML配置和Java配置
```
XML配置：在/resources/目录下创建xml文件，例如beans.xml
通过 @ImportResource 注解加载配置文件，例子

@Configuration
@ImportResource("classpath:beans.xml")
public class Beans{}

Java配置：配置类需要加 @Configuration 注解
```

#### 注册拦截器
```
1. 创建拦截器实现HandlerInterceptor接口
public class MyInterceptor implements HandlerInterceptor{
	//拦截器中的方法按 preHandle -> Controller -> postHandle -> afterCompletion 的顺序执行。

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		// 如果返回false, 则后面的方法都不会执行
		return true
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
		// 当preHandle返回true时，此方法才会被执行。
	}
}

2. 配置拦截器
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		// 拦截所有请求，"/login" 除外
		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns("/login");
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

#### 面向切面编程AOP
```
Joinpoint 连接点：
	类里面可以被增强的方法即为连接点。

Pointcut 切入点：
		对Joinpoint进行拦截的定义即为切入点。
		
Advice 通知：
		拦截到Joinpoint之后所要做的事情即为通知。
		通知有 前置通知、后置通知、异常通知、最终通知、环绕通知。

Aspect 切面：
		Pointcut和Advice的结合。
		
Targer 目标对象：
		要增强的类即称为Target。
		

AOP使用

1. 依赖
	spring-boot-starter-aop
	
2. 创建切面配置
@Component
@Aspect
public class LogAspect{
	//对所有方法增强
	@Pointcut("execution(* com.xpit.aop.service.*.*(..))")
	public void pc1(){}
	
	//前置通知: 在被增强的方法执行前执行
	@Before(value="pc1()")
	public void before(Joinpoint jp){
		//获取被增强的方法名
		String name = jp.getSignature().getName();
	}
	
	//后置通知：在增强的方法执行之后执行
	@After(value="pc1()")
	public void after(Joinpoint jp){
	
	}
	
	//当被增强方法有返回值时执行
	@AfterReturning(value="pc1()", returning="result")
	public void afterReturning(Joinpoint jp, Object result){
		// result为被增强方法的return返回值
	}
	
	//异常通知：在被增强方法执行异常时执行
	@AfterThrowing(value="pc1()", throwing="e")
	public void afterThrowing(Joinpoint jp, Exception e){
		// 获取被增强的方法抛出的异常 e.getMessage()
	}
	
	//环绕通知：在被增强方法执行的前、后执行
	@Around("pc1()")
	public Object around(ProceedingJoinPoin pjp) throws Throwable{
		return pjp.proceed();
	}
}	
```
	
