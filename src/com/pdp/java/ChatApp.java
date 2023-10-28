package com.pdp.java;

import com.pdp.java.entity.Chat;
import com.pdp.java.entity.User;
import com.pdp.java.service.api.ChatService;
import com.pdp.java.service.api.UserService;
import com.pdp.java.service.impl.ChatServiceImpl;
import com.pdp.java.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Scanner;

public class ChatApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserServiceImpl();
        ChatService chatService = new ChatServiceImpl();

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice == 1) {
                // Register a user
                System.out.print("Enter your email: ");
                String email = scanner.nextLine();
                System.out.print("Enter your first name: ");
                String firstName = scanner.nextLine();
                System.out.print("Enter your last name: ");
                String lastName = scanner.nextLine();

                User user = new User(email, firstName, lastName);
                if (userService.register(user)) {
                    System.out.println("Registration successful.");
                } else {
                    System.out.println("Registration failed.");
                }
            } else if (choice == 2) {
                // Login
                System.out.print("Enter your email: ");
                String email = scanner.nextLine();
                User loggedInUser = userService.login(email);
                if (loggedInUser != null) {
                    System.out.println("Login successful. Welcome, " + loggedInUser.getFirstName() + "!");
                    chatMenu(scanner, loggedInUser, chatService, userService);
                } else {
                    System.out.println("Login failed. User not found.");
                }
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void chatMenu(Scanner scanner, User user, ChatService chatService, UserService userService) {
        while (true) {
            System.out.println("1. Send a message");
            System.out.println("2. View chat history");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice == 1) {
                System.out.print("Enter recipient's email: ");
                String recipientEmail = scanner.nextLine();
                User recipient = userService.getUserByEmail(recipientEmail);
                if (recipient == null) {
                    System.out.println("Recipient not found.");
                } else {
                    System.out.print("Enter your message: ");
                    String message = scanner.nextLine();
                    chatService.sendMessage(user, recipient, message);
                    System.out.println("Message sent.");
                }
            } else if (choice == 2) {
                List<Chat> chatHistory = chatService.getChatHistory(user);
                for (Chat chat : chatHistory) {
                    System.out.println(chat.getSender().getFirstName() + ": " + chat.getMessage() + " (" + chat.getTimestamp() + ")");
                }
            } else if (choice == 3) {
                System.out.println("Logged out.");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}