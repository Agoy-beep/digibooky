package com.testingtigers.api.security;

import com.testingtigers.domain.users.Admin;
import com.testingtigers.domain.users.Authenticatable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthenticationService {

    private List<Authenticatable> userAuthenticatons;

    public UserAuthenticationService() {
        userAuthenticatons = new ArrayList<>();
        userAuthenticatons.add(new Admin("admin", "admin", "admin@admin.com"));
    }

    public Authenticatable getUser(String email, String password) {
        return userAuthenticatons.stream()
                .filter(authenticatable -> authenticatable.getEmail().equals(email))
                .filter(authenticatable -> authenticatable.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
