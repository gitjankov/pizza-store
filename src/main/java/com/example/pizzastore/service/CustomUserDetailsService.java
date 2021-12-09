package com.example.pizzastore.service;

import com.example.pizzastore.model.User;
import com.example.pizzastore.repsitory.JwtUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    JwtUserRepository jwtUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User jwtUser = jwtUserRepository.findUserByEmail(email);
        if (jwtUser == null) {
            throw new UsernameNotFoundException("Username not found" + email);
        }
        return new org.springframework.security.core.userdetails.User(jwtUser.getEmail(), jwtUser.getPassword(), new ArrayList<>());
    }
}
