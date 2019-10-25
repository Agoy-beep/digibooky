package com.testingtigers.api.security;

import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    public static final String NAME_OF_REALM = "userRealm";
}
