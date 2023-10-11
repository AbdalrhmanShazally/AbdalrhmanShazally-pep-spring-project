package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

    //Custom Query to get message by its ID
    @Query("SELECT a FROM Message a WHERE a.message_id = :message_id")
    Optional<Message> findMessageById(@Param("message_id") int message_id);

    //Custom Query to get List of Messages using posted_by"account_id"
    @Query("SELECT a FROM Message a WHERE a.posted_by = :posted_by")
    List<Message> findMessageByPostedBy(@Param("posted_by") int posted_by);

    //Custom DML to delete message by its  Id and get the num of rows deleted
    @Modifying
    @Query("DELETE FROM Message a WHERE a.message_id = :message_id")
    int deleteMessageById(@Param("message_id") int message_id);

    //Custom DML to update message by its  Id and get the num of rows updated
    @Modifying
    @Query("UPDATE Message a SET a.message_text = :message_text WHERE a.message_id = :message_id")
    int updateMessageById(@Param("message_text") String message_text , @Param("message_id") int message_id);



}
