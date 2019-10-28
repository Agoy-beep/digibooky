package com.testingtigers.domain.repositories;


import com.testingtigers.domain.users.Admin;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdminRepository {

    private final Map<String, Admin> admins;

    public AdminRepository() {
        this.admins = new HashMap<>();
        Admin admin = new Admin("admin", "admin", "admin@admin.com");
        admins.put(admin.getId(), admin);
    }

    public Collection<Admin> getAll() {
        return admins.values();
    }

    public Admin adAdmin(Admin adminToAdd) {
        admins.put(adminToAdd.getId(), adminToAdd);
        return adminToAdd;
    }
}
