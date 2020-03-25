# spring-boot
spring boot 开发技术整理

## spring-boot项目启动方式
1. mvn spring-boot:run
2. 启动类直接运行主方法
3. 打包jar  
	```
	<packaging>jar</packaging>  
	mvn clean package  
	java -jar xxx.jar
	```
4. 打包war,发布tomcat运行  
	```
	<packaging>war</packaging>  
	mvn clean package  
	将war包放入apache-tomcat-8.5.50\webapps\目录下  
	启动tomcat服务即可访问
	```
	
## 自定义启动banner
只需在resources目录下创建一个banner.txt文件即可  
在线生成banner字体  
www.network-science.de/ascii/

## 常用的配置
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
