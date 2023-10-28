package com.pdp.java.service.impl;

import com.pdp.java.entity.Chat;
import com.pdp.java.entity.User;
import com.pdp.java.service.api.ChatService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl implements ChatService {
    private List<Chat> chatHistory = new ArrayList<>();
    private String chatHistoryFilePath = "chats.dat"; // File to store chat history

    public ChatServiceImpl() {
        loadChatHistoryFromFile();
    }

    @Override
    public void sendMessage(User sender, User recipient, String message) {
        Chat chat = new Chat(sender, recipient, message);
        chatHistory.add(chat);
        saveChatHistoryToFile();
    }

    @Override
    public List<Chat> getChatHistory(User user) {
        List<Chat> userChatHistory = new ArrayList<>();
        for (Chat chat : chatHistory) {
            if (chat.getSender().getEmail().equals(user.getEmail()) || chat.getRecipient().getEmail().equals(user.getEmail())) {
                userChatHistory.add(chat);
            }
        }
        return userChatHistory;
    }

    private void saveChatHistoryToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chatHistoryFilePath))) {
            oos.writeObject(chatHistory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadChatHistoryFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chatHistoryFilePath))) {
            chatHistory = (List<Chat>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            chatHistory = new ArrayList<>();
        }
    }
}