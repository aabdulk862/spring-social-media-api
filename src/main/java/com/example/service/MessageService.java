package com.example.service;

import com.example.entity.Message;
import com.example.exception.BadRequestException;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
