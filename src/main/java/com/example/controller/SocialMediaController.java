package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountInterfaceService;
import com.example.service.MessageInterfaceService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
 public class SocialMediaController {

    //Let Spring Detect our AccountService Class
    @Autowired
    AccountInterfaceService accountInterfaceService;

    @Autowired
    MessageInterfaceService messageInterfaceService;

    /*
     * Service Name: register
     * Request Type: Post.
     * Request Body JSON of Account entity with no account_id.
     * Response : JSON of Account entity contains account_id if registeration done successfully. or HTTP Code 400
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account){
        Optional<Account> registeredAccount = Optional.ofNullable(accountInterfaceService.addAccountService(account));
        if(registeredAccount.isPresent()) {
            return new ResponseEntity<>(registeredAccount.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    /*
     * Service Name: login
     * Request Type: Post.
     * Request Body JSON of Account entity with no account_id.
     * Response : JSON of Account entity contains account_id if login done successfully. or HTTP Code 401
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        Optional<Account> existAccount = Optional.ofNullable(accountInterfaceService.login(account.getUsername(), account.getPassword()));
        if(existAccount.isPresent()) {
            return  new ResponseEntity<>(existAccount.get() , HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /*
     * Service Name: createMessage
     * Request Type: Post.
     * Request Body JSON of message entity with no message_id.
     * Response : JSON of message entity contains message_id if create done successfully. or HTTP Code 400
     */
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message){
        Optional<Message> createdMessage = Optional.ofNullable(messageInterfaceService.addMessageService(message));
        if(createdMessage.isPresent()){
            return new ResponseEntity<>(createdMessage.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /*
     * Service Name: showAllMessages
     * Request Type: Get.
     * Request Body none.
     * Response : JSON of list all messages if successfully. null if no message found.
     */
    @GetMapping("/messages")
    public ResponseEntity<?> showAllMessages(){
        List<Message> messages = messageInterfaceService.showAllMessages();
        if(messages.size() > 0){
            return new ResponseEntity<>(messages,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * Service Name: showMessage
     * Request Type: Get.
     * Request Body none.
     * URL Parameter: message_id integer of message_id to be retrieved from DB.
     * Response : JSON of message if successfull. null if no message found.
     */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<?> showMessage(@PathVariable("message_id") Integer messageId){
        Optional<Message> exitsmessage = messageInterfaceService.findMessageById(messageId);
        if(exitsmessage.isPresent()) {
            return new ResponseEntity<>(exitsmessage.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
     * Service Name: deleteMessage
     * Request Type: Delete.
     * Request Body none.
     * URL Parameter: message_id integer of message_id to be deleted from DB.
     * Response : number of rows deleted or empty if no message witht the given id.
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable("message_id") Integer messageId){
      Optional<Integer> rowsdeleted = Optional.ofNullable(messageInterfaceService.deleteMessageById(messageId));

       if(rowsdeleted.isPresent()) {
           return new ResponseEntity<>(rowsdeleted.get(), HttpStatus.OK);
       } else
           return new ResponseEntity<>(HttpStatus.OK);

    }

     /*
     * Service Name: updateMessage
     * Request Type: Patch.
     * Request Body Json contain message_text to be updated.
     * URL Parameter: message_id integer of message_id to be update.
     * Response : number of rows updated or empty if no message witht the given id.
     */
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable("message_id") Integer messageId,@RequestBody Message message){
        Optional<Integer> rows_updated = Optional.ofNullable(messageInterfaceService.updateMessageById(messageId,message));

        if (rows_updated.isPresent()) {
            return new ResponseEntity<>(rows_updated.get(), HttpStatus.OK);
        } else
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /*
     * Service Name: showAllMessagesById
     * Request Type: Get.
     * Request Body none.
     * URL Parameter: account_id integer of posted_by to be retrieved.
     * Response : list of all messages posted by that user or null if no message.
     */
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<?> showAllMessagesById(@PathVariable("account_id") Integer account_id){
        List<Message> messages = messageInterfaceService.showAllMessagesByPostedBy(account_id);
   //     if(messages.size() > 0) {
            return new ResponseEntity<>(messages, HttpStatus.OK);
    //    }
    //        return new ResponseEntity<>(HttpStatus.OK);
    }

}
