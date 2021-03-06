package com.example.application.backend.security.service;

import com.example.application.backend.model.User;

import com.example.application.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Autentica un usuario de la base de datos
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private User userLogged;

    @Override
    public UserDetails loadUserByUsername(String nif) throws UsernameNotFoundException {
        User user = userRepository.findOneByNif(nif)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with nif: " + nif));

        userLogged = user;

        return new org.springframework.security.core.userdetails.User(
                user.getNif(),user.getPassword(),new ArrayList<>());
    }

    public User getUserLogged() {
        return userLogged;
    }

    public void setUserLogged(User userLogged) {
        this.userLogged = userLogged;
    }

    public static UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
