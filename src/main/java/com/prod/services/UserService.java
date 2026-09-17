package com.prod.services;


import com.prod.entities.User;
import com.prod.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails=userRepository.findByEmail(username).orElseThrow();
        return userDetails;
    }

    public User loadUserByUserId(Long id){
        return userRepository.findById(id).orElseThrow();
    }


}
