package com.quest.etna.service;

import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public JwtUserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (null == user || ! user.getUsername().equals(username)) {
            throw new UsernameNotFoundException("User not found: " + username);
        } else {
            return new JwtUserDetails(user);
        }
    }
}
