package ru.dusniz.listentimechatswebsocketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.dusniz.listentimechatswebsocketservice.dto.ChatRequest;
import ru.dusniz.listentimechatswebsocketservice.dto.ChatResponse;
import ru.dusniz.listentimechatswebsocketservice.model.Message;
import ru.dusniz.listentimechatswebsocketservice.service.ChatService;
import ru.dusniz.listentimechatswebsocketservice.service.MessageService;

import java.util.List;

@Controller
public class ChatController {

    @Autowired private SimpMessagingTemplate messagingTemplate;
    @Autowired private MessageService messageService;
    @Autowired private ChatService chatService;


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

    @GetMapping("/chats")
    public ChatResponse getAllChats() {
        return ChatResponse.builder()
                .chats(chatService.getAllChats())
                .build();
    }

    @PostMapping("/chats")
    public ChatResponse createChat(ChatRequest chatRequest) {
        var savedChat = chatService.saveChat(chatRequest.getUserIds());
        return ChatResponse.builder().chats(List.of(savedChat)).build();
    }

    @PostMapping("/chats/{chatId}")
    public void addUserToChat(@PathVariable Integer chatId, Integer userId) {
        var chat = chatService.getChatById(chatId);
        chat.setUsersIds(chat.getUsersIds() + String.valueOf(userId) + ",");
        chatService.updateUserIdsInChat(chatId, userId);
    }

    @GetMapping("/chat")
    public ChatResponse getChatById(Integer chatId) {
        var chat = chatService.getChatById(chatId);
        return ChatResponse.builder()
                .chats(List.of(chat))
                .build();
    }
}
