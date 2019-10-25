package com.testingtigers.api.security;

import com.testingtigers.domain.users.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final UserAuthenticationService userAuthService;

    @Autowired
    public UserAuthenticationProvider(UserAuthenticationService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authenticatable user = userAuthService.getUser(authentication.getPrincipal().toString(), authentication.getCredentials().toString());
        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), convertFeaturesToAuthorities(Feature.getFeatures(user.getRole().toString())));
        }
        throw new BadCredentialsException("Incorrect credentials.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Collection<? extends GrantedAuthority> convertFeaturesToAuthorities(List<Feature> roles) {
        return roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
