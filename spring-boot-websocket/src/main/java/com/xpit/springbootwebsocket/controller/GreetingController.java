package com.xpit.springbootwebsocket.controller;

import com.xpit.springbootwebsocket.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    /*@MessageMapping("/hello")
    @SendTo("/topic/greeting")
    public Message greeting(Message message) throws Exception {
        return message;
    }*/

    @MessageMapping("/hello")
    public void greeting(Message message) throws Exception {
        messagingTemplate.convertAndSend("/topic/greeting", message);
    }
}
