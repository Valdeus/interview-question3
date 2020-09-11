package com.example.demo.persistence;

import com.example.demo.persistence.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository
        extends JpaRepository<MessageEntity, Long> {

    public List<MessageEntity> findByQuestionId(Long questionId);

    @Query("select m from MessageEntity m where m.id = ?1 and questionId is NULL OR m.id = (select r.questionId from MessageEntity r where r.id = ?1)")
    public MessageEntity findThread(Long questionId);
}
