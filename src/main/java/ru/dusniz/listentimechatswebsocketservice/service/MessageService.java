package ru.dusniz.listentimechatswebsocketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dusniz.listentimechatswebsocketservice.model.Message;
import ru.dusniz.listentimechatswebsocketservice.repository.MessageRepository;

import java.util.Optional;

@Service
public class MessageService {

    @Autowired private MessageRepository repository;

    public Optional<Message> getMessageById(Integer id) {
        return repository.findById(id);
    }

    public Message saveMessage(Integer chatId, Integer senderId, String content) {
        var message = Message.builder()
                .chatId(chatId)
                .senderId(senderId)
                .content(content)
                .build();
        return repository.save(message);
    }

}
