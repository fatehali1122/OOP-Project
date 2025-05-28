package com.bloodBank;

import java.util.HashMap;
import java.util.Map;

public class LoginManager {
    private static final Map<String, String> credentials = new HashMap<>();

    static {
        credentials.put("admin", "admin123"); // Add your own
        credentials.put("user", "user123");
    }

    public static boolean login(String username, String password) {
        return credentials.containsKey(username) && credentials.get(username).equals(password);
    }
}
