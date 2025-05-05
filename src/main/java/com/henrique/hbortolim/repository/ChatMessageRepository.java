package com.henrique.hbortolim.repository;

import com.henrique.hbortolim.model.ChatMessage;
import com.henrique.hbortolim.model.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByTypeOrderByTimestampAsc(MessageType type);

    List<ChatMessage> findAllByOrderByTimestampAsc();
}