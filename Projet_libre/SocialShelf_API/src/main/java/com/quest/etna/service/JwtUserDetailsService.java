package com.quest.etna.service;

import com.quest.etna.model.jwt.JwtUserDetails;
import com.quest.etna.model.player.Player;
import com.quest.etna.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
    private UserRepository userRepository;

    @Override
    public JwtUserDetails loadUserByUsername(String username) {
        Player user = userRepository.findByUsernameIgnoreCase(username);
        if (null == user || ! user.getUsername().equalsIgnoreCase(username)) {
            throw new UsernameNotFoundException("User not found: " + username);
        } else {
            return new JwtUserDetails(user);
        }
    }
}
