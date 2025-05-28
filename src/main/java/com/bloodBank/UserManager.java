package com.bloodBank;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String USER_FILE = "users.txt";

    // Register a new user (write to file)
    public static void registerUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing user: " + e.getMessage());
        }
    }

    // Check if username already exists
    public static boolean isUsernameTaken(String username) {
        List<String> users = readAllUsers();
        for (String line : users) {
            String[] parts = line.split(",");
            if (parts.length >= 1 && parts[0].equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Validate login
    public static boolean validateLogin(String username, String password) {
        List<String> users = readAllUsers();
        for (String line : users) {
            String[] parts = line.split(",");
            if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Helper method to read all users
    private static List<String> readAllUsers() {
        List<String> lines = new ArrayList<>();
        File file = new File(USER_FILE);
        if (!file.exists()) return lines;

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading users: " + e.getMessage());
        }
        return lines;
    }
}
