package com.testingtigers.api.security;

import com.testingtigers.domain.repositories.AdminRepository;
import com.testingtigers.domain.repositories.MemberRepository;
import com.testingtigers.domain.users.Authenticatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService {

    private MemberRepository members;
    private AdminRepository admins;

    @Autowired
    public UserAuthenticationService(MemberRepository members, AdminRepository admins) {
        this.members = members;
        this.admins = admins;
    }

    public Authenticatable getUser(String email, String password) {
        Authenticatable result = admins.getAll().stream()
                .filter(authenticatable -> authenticatable.getEmail().equals(email))
                .filter(authenticatable -> authenticatable.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (result != null) {
            return result;
        }
        return members.getAll().stream()
                .filter(authenticatable -> authenticatable.getEmail().equals(email))
                .filter(authenticatable -> authenticatable.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
