package com.pdp.java.service.impl;

import com.pdp.java.ChatApp;
import com.pdp.java.entity.User;
import com.pdp.java.service.api.UserService;
import com.pdp.java.validators.UserInfoValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger("MyLogger");

    static {
        try {
            LogManager.getLogManager().readConfiguration(ChatApp.class.getClassLoader().getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Exception happened while reading .properties file", e);
        }
    }

    private List<User> users = new ArrayList<>();
    private String usersFilePath = "users.dat"; // File to store user data

    public UserServiceImpl() {
        loadUsersFromFile();
    }

    @Override
    public boolean register(User user) {
        if (getUserByEmail(user.getEmail()) != null) {
            return false;
        }

        if (!UserInfoValidator.isValidEmail(user.getEmail())) {
            return false;
        }

        if (!UserInfoValidator.isValidName(user.getFirstName()) || !UserInfoValidator.isValidName(user.getLastName())) {
            return false;
        }

        users.add(user);
        saveUsersToFile();
        logger.log(new LogRecord(Level.INFO, String.format("Successful registration: %s", user)));
        return true;
    }

    @Override
    public User login(String email) {
        return getUserByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    private void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFilePath))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadUsersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFilePath))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            users = new ArrayList<>();
        }
    }

    @Override
    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}