package com.henrique.hbortolim.repository;

import com.henrique.hbortolim.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    List<ChatEntity> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<ChatEntity> findByUserIdAndIsArchivedFalseOrderByCreatedAtDesc(Long userId);

    List<ChatEntity> findByUserIdAndIsArchivedTrueOrderByCreatedAtDesc(Long userId);

    @Query("SELECT c FROM ChatEntity c WHERE c.user.id = :userId AND c.id = :chatId")
    Optional<ChatEntity> findByUserIdAndId(@Param("userId") Long userId, @Param("chatId") Long chatId);

    boolean existsByUserIdAndId(Long userId, Long chatId);

    long countByUserIdAndIsArchivedFalse(Long userId);
} 