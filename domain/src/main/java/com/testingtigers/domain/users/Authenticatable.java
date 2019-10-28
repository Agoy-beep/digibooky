package com.testingtigers.domain.users;

public interface Authenticatable {

    String getEmail();

    String getPassword();

    Role getRole();
}
