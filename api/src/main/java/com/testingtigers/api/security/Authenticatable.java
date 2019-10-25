package com.testingtigers.api.security;

import com.testingtigers.domain.users.Role;

public interface Authenticatable {

    public String getEmail();

    public String getPassword();

    public Role getRole();
}
