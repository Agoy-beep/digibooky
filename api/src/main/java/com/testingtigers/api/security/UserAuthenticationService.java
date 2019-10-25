package com.testingtigers.api.security;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthenticationService {

    private List<Authenticatable> userAuthenticatons;

    public UserAuthenticationService() {
        userAuthenticatons = new ArrayList<>();
    }

    public Authenticatable getUser(String email, String password) {
        return userAuthenticatons.stream()
                .filter(authenticatable -> authenticatable.getEmail().equals(email))
                .filter(authenticatable -> authenticatable.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
