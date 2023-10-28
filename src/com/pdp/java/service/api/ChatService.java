package com.pdp.java.service.api;

import com.pdp.java.entity.Chat;
import com.pdp.java.entity.User;

import java.util.List;

public interface ChatService {
    void sendMessage(User sender, User recipient, String message);

    List<Chat> getChatHistory(User user);
}