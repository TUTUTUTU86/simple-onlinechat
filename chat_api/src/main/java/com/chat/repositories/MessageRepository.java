package com.chat.repositories;

import com.chat.entities.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface MessageRepository extends CrudRepository<Message, Long> {
    public Iterable<Message> findMessagesByCreatedAtAfter(Date date);
}
