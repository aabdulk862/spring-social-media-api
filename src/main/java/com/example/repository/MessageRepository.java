package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Message m WHERE m.messageId = :id")
    int deleteMessageById(int id);
    boolean existsByPostedBy(int postedById);
    List<Message> findAllByPostedBy(int postedBy);
}
