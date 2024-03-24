package ru.dusniz.listentimechatswebsocketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.dusniz.listentimechatswebsocketservice.model.Message;
import ru.dusniz.listentimechatswebsocketservice.service.MessageService;

@Controller
public class MessageController {
    @Autowired private SimpMessagingTemplate messagingTemplate;
    @Autowired private MessageService messageService;

    @MessageMapping("/messages")
    public void sendMessage(@Payload Message message) throws InterruptedException {
        var savedMessage = messageService.saveMessage(
                message.getChatId(),
                message.getSenderId(),
                message.getContent()
        );

        messagingTemplate.convertAndSendToUser(
                String.valueOf(savedMessage.getChatId()),
                "/queue/messages",
                savedMessage
        );
    }
}
