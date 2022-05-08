package com.xendv.ReportLoader.service;

import com.xendv.ReportLoader.model.SystemUser;
import com.xendv.ReportLoader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Override
    public SystemUser create(SystemUser user) {
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public SystemUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User principal = (User) authentication.getPrincipal();
        return userRepository.findByLogin(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Current user not found"));
    }

    //@Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        SystemUser user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " not found"));

        return new User(
                user.getLogin(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }

}
