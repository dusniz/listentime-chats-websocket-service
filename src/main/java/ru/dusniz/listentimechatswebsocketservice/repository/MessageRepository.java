package ru.dusniz.listentimechatswebsocketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dusniz.listentimechatswebsocketservice.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
