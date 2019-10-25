package com.testingtigers.domain.users;

public interface Authenticatable {

    public String getEmail();

    public String getPassword();

    public Role getRole();
}
