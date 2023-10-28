package com.pdp.java.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Chat implements Serializable {
    private User sender;
    private User recipient;
    private String message;
    private LocalDateTime timestamp;

    public Chat(User sender, User recipient, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}