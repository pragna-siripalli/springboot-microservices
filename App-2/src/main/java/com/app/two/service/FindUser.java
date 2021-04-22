package com.app.two.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class FindUser implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        com.app.two.service.User user = restTemplate.getForObject("http://40.121.137.58:30000/validateUser/" + username,
                User.class);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                new ArrayList<>());
    }
}
