package com.scaler.userservicemwfeve.security.services;

import com.scaler.userservicemwfeve.models.User;
import com.scaler.userservicemwfeve.repositories.UserRepository;
import com.scaler.userservicemwfeve.security.models.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(username);

        if(userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + username + " is not found.");
        }

        return new CustomUserDetails(userOptional.get());
    }
}
