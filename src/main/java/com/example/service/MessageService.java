package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService implements MessageInterfaceService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountService accountService;

    /**
     * addMessageService:  method to implement User post new message Service requirements.
     * The message_text is not blank, is under 255 characters.
     * posted_by refers to a real, existing user under db.
     * this method depends on 2 private mehtods all implemented in this class.
     * @param message: a message object to be cheked.
     * @return message object contain message_id if the message meet requirements , null if the message dosn't meet the requirements.
     */
    @Override
    public Message addMessageService(Message message) {
        if(isValidMessage(message)){
            if(accountExists(message.getPosted_by())){
                Message savedMessage = messageRepository.save(message);
                return savedMessage;
            }
        }
        return null;
    }


    /**
     * showAllMessages:  method to get all messages from DB.
     * @return List of messages.
     */
    @Override
    public List<Message> showAllMessages() {
        return messageRepository.findAll();
    }
    
    /**
     * findMessageById:  method to get  message from DB based on its id.
     * @param message_id: integer message_id.
     * @return Optional Message may or may not contain message.
     */
    @Override
    public Optional<Message> findMessageById(int message_id) {
        Optional<Message> existMessage = messageRepository.findById(message_id);
        return existMessage;
    }

    /**
     * deleteMessageById:  method to delete  message from DB based on its id.
     * @param message_id: integer message_id.
     * @return number or rows affected by delete statement.
     */
    @Override
    @Transactional
    public Integer deleteMessageById(int message_id) {
        return messageRepository.deleteMessageById(message_id);
         
    }

    /**
     * updateMessageById:  method to update  message_text based on its id.
     * before updating the message certin requirements must be checked using isValidMessage function.
     * @param message_id: integer message_id.
     * @param message : message object contain message_text.
     * @return number or rows affected by update statement.
     */
    @Override
    @Transactional
    public Integer updateMessageById(int message_id, Message message) {
        Optional<Message> existMessage = findMessageById(message_id);
        if(existMessage.isPresent()) {
            if(isValidMessage(message)) {
                return messageRepository.updateMessageById(message.getMessage_text(), message_id);
            }
        }
        return null;
    }

    /**
     * showAllMessagesByPostedBy:  method to get all messages from DB based on posted_by.
     * @return List of messages.
     */
    @Override
    public List<Message> showAllMessagesByPostedBy(int posted_by) {
        
        return messageRepository.findMessageByPostedBy(posted_by);
    }


    /**
     * isValidMessage: private method to implement message_text requirements will be invoked with addMessage.
     * The message_text shouldn't be blank or the length more  than 255 length.
     * @param message: an message object to be cheked.
     * @return True if the message meet requirements , False if the message dont.
     */
    private boolean isValidMessage(Message message) {
        return !message.getMessage_text().isBlank() && message.getMessage_text().length() <= 255;
    }

    /**
     * accountExists: private method to implement posted_by requirements will be invoked with addMessage.
     * The account shouldn't be exist under DB.
     * @param userId: integer userId to be checked in the DB.
     * @return True if the account exits , False if not exists.
     */
    private boolean accountExists(Integer userId){
        Optional<Account> existingAccount  = accountService.findById(userId);
        return existingAccount.isPresent();
    }
    
}
