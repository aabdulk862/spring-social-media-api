package com.example.service;

import com.example.entity.Message;
import com.example.exception.BadRequestException;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        // Validate message_text
        if (message.getMessageText() == null || message.getMessageText().isEmpty() ||
                message.getMessageText().length() > 255) {
            throw new BadRequestException("Message text must not be blank and must be 1-255 characters long");
        }

        // Check if posted_by exists (assuming posted_by is the ID of the user)
        if (!messageRepository.existsByPostedBy(message.getPostedBy())) {
            throw new BadRequestException("PostedBy must refer to an existing user");
        }

        // Save the message and return it with message_id populated
        return messageRepository.save(message);
    }

    public List<Message> getMessages() {
        return  messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int id) {
        return messageRepository.findById(id);
    }

    public int deleteMessageById(int id) {
        return messageRepository.deleteMessageById(id);
    }

    public Message updateMessage(Message message) {
        Optional<Message> messageOptional = messageRepository.findById(message.getMessageId());
        if (messageOptional.isPresent()) {
            return messageRepository.save(message);
        }
        return null;
    }

    public List<Message> getAllMessagesByUserId(int postedBy) {
        return messageRepository.findAllByPostedBy(postedBy);
    }


}
