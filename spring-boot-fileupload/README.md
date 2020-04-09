# spring-boot
spring boot 文件上传

#### 依赖
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

#### 文件上传相关配置
```
#是否支持 multipart 上传文件
spring.servlet.multipart.enabled=true
#支持文件写入磁盘
spring.servlet.multipart.file-size-threshold=0
#上传文件的临时目录
spring.servlet.multipart.location=/tmp
#最大支持文件大小
spring.servlet.multipart.max-file-size=10MB
#最大支持请求大小
spring.servlet.multipart.max-request-sizee=10MB
#是否支持 multipart 上传文件时懒加载
spring.servlet.multipart.resolve-lazily=false
```

#### 前端页面
```
```

