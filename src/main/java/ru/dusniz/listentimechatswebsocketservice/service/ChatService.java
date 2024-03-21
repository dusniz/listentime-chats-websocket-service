package ru.dusniz.listentimechatswebsocketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dusniz.listentimechatswebsocketservice.model.Chat;
import ru.dusniz.listentimechatswebsocketservice.repository.ChatRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired private ChatRepository chatRepository;

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public Chat saveChat(String userIds) {
        var chat = Chat.builder()
                .usersIds(userIds)
                .build();
        return chatRepository.save(chat);
    }

    public Chat getChatById(Integer chatId) {
        return chatRepository.getById(chatId);
    }

    public void updateUserIdsInChat(Integer chatId, Integer userId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new NoSuchElementException("chat with such id does not exist"));
        chat.setUsersIds(chat.getUsersIds() + String.valueOf(userId) + ",");
        chatRepository.save(chat);
    }
}

