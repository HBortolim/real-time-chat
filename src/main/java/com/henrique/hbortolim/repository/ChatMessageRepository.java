package com.henrique.hbortolim.repository;

import com.henrique.hbortolim.entity.ChatMessageEntity;
import com.henrique.hbortolim.enums.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    List<ChatMessageEntity> findByTypeOrderByTimestampAsc(MessageType type);

    List<ChatMessageEntity> findAllByOrderByTimestampAsc();
}