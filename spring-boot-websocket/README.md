# spring-boot-websocket
spring boot 整合 websocket

#### 依赖
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-websocket</artifactId>
</dependency>

<!--以下依赖是前端库-->
<dependency>
	<groupId>ore.webjars</groupId>
	<artifactId>webjars-locator-core</artifactId>
</dependency>
<dependency>
	<groupId>org.webjars</groupId>
	<artifactId>sockjs-client</artifactId>
	<version>1.1.2</version>
</dependency>
<dependency>
	<groupId>org.webjars</groupId>
	<artifactId>stomp-websocket</artifactId>
	<version>2.3.3</version>
</dependency>
<dependency>
	<groupId>org.webjars</groupId>
	<artifactId>jquery</artifactId>
	<version>3.3.1</version>
</dependency>
```

#### 配置WebSocket
```
// EnableWebSocketMessageBroker 注解开启websocket消息代理
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
		// 设置消息代理前缀，如果消息的前缀是该设置的值就会将消息转发给消息代理broker
        config.enableSimpleBroker("/topic");
		// 配置一个或多个前缀
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 
        registry.addEndpoint("/chat").withSockJS();
    }
}

```

#### 消息实体
```
public class Message {
    private String name;
    private String content;
	//getter setter toString
}	
```

#### 控制层
```
@RestController
public class GreetingController {

	// 方式1
	@MessageMapping("/hello")
    @SendTo("/topic/greeting")
    public Message greeting(Message message) throws Exception {
        return message;
    }

	// 方式2
    @Autowired
    SimpMessagingTemplate messagingTemplate;
	@MessageMapping("/hello")
    public void greeting(Message message) throws Exception {
        messagingTemplate.convertAndSend("/topic/greeting", message);
    }    
}

```

#### 群聊页面
```
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>聊天室-群聊</title>
    <script src="/webjars/jquery/3.3.1/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
    <script src="webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
</head>
<body>
<div>
    <label for="name">请输入用户名：</label>
    <input type="text" id="name" placeholder="用户名">
</div>
<div>
    <button id="connect" type="button" onclick="connect()">连接</button>
    <button id="disconnect" type="button" onclick="disconnect()" disabled="disabled">断开连接</button>
</div>
<div id="chat" style="display: none;">
    <div>
        <label>请输入聊天内容：</label>
        <input type="text" id="content" placeholder="聊天内容">
    </div>
    <button id="send" type="button" onclick="sendName()">发送</button>
    <div id="greetings">
        <div id="conversation" style="display: none;">群聊进行中...</div>
    </div>
</div>

<script>
    var stompClient = null;

    function setConnected(connected) {
        $('#connect').prop('disabled', connected);
        $('#disconnect').prop('disabled', !connected);
        if (connected) {
            $('#conversation').show();
            $('#chat').show();
        } else {
            $('#conversation').hide();
            $('#chat').hide();
        }
        $('#greetings').html("");
    }

    function connect() {
        console.log('打开连接ws')
        if (!$('#name').val()) {
            console.log('请输入用户名');
            return;
        }
        var socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            stompClient.subscribe('/topic/greeting', function (greeting) {
                showGreeting(JSON.parse(greeting.body));
            })
        })
    }

    function disconnect() {
        if (stompClient !== null) {
            console.log('断开连接')
            stompClient.disconnect();
        }
        setConnected(false);
    }

    function sendName() {
        stompClient.send('/app/hello', {}, JSON.stringify({
            'name': $('#name').val(),
            'content': $('#content').val()
        }))
    }

    function showGreeting(message) {
        $('#greetings').append('<div>' + message.name + ' : ' + message.content + '</div>>');
    }


    $(function () {
        // $('#content').click(function () {
        //     connect();
        // });
        // $('#disconnect').click(function () {
        //     disconnect();
        // });
        // $('#send').click(function () {
        //     sendName();
        // });
    })

</script>
</body>
</html>
```
