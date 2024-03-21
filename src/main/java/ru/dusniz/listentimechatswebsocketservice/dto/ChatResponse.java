package ru.dusniz.listentimechatswebsocketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dusniz.listentimechatswebsocketservice.model.Chat;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponse {

    private List<Chat> chats;
}
