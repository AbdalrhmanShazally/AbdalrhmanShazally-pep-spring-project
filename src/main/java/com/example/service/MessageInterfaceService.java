package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Message;
/*
 * this MessageInterfaceService interface will be Abstraction of MessageService Class 
 * @version 1.0 10-10-2023
 * @author Abdalrhman Alhassan
 */
public interface MessageInterfaceService {

    Message addMessageService(Message message);
    List<Message> showAllMessages();
    
    Optional<Message> findMessageById(int message_id);

    Integer deleteMessageById(int message_id);

    Integer updateMessageById(int message_id , Message message);

    List<Message> showAllMessagesByPostedBy(int posted_by);   
    
}
