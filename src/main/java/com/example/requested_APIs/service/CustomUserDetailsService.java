package com.example.requested_APIs.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Your logic to load the user from the database
        // Example, return a hardcoded user:
        return User.builder()
                .username(username)
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }
}
