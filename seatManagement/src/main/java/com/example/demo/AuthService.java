package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String GUEST_USERNAME = "guest";
    private static final String GUEST_PASSWORD = "guest123";

    private final Map<String, User> users = new HashMap<>();

    public AuthService() {
        register(GUEST_USERNAME, GUEST_PASSWORD);
    }

    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password));
        return true;
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }
}
