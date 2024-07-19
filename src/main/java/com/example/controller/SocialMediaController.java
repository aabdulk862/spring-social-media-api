package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.BadRequestException;
import com.example.exception.ConflictException;
import com.example.exception.UnauthorizedException;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account registeredAccount = accountService.register(account);
        return ResponseEntity.ok(registeredAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account loginRequest) {
        Account account = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(account);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
        List<Message> messages = messageService.getMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int id) {
        Optional<Message> message = messageService.getMessageById(id);
        return message.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.ok().build());
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int id) {
        int rowsAffected = messageService.deleteMessageById(id);
        if (rowsAffected > 0) {
            return ResponseEntity.ok(rowsAffected);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PatchMapping("/messages/{id}")
    public ResponseEntity<?> patchMessageText(@PathVariable int id, @RequestBody Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().isEmpty() || message.getMessageText().trim().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message cannot be empty");
        } else if (message.getMessageText().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message cannot be more than 255 characters long");
        }

        Optional<Message> messageOptional = messageService.getMessageById(id);
        
        if (!messageOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message does not exist");
        }

        return ResponseEntity.ok(1);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> findMessagesByUserId(@PathVariable int accountId) {
        return new ResponseEntity<List<Message>>(messageService.getAllMessagesByUserId(accountId), HttpStatus.OK);
    }

    // Exception handlers
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflictException(ConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}
