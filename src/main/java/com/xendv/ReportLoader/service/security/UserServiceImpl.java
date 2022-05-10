package com.xendv.ReportLoader.service.security;

import com.xendv.ReportLoader.model.SystemUser;
import com.xendv.ReportLoader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.xendv.ReportLoader.conf.SecurityConfig.passwordEncoder;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public SystemUser create(SystemUser user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public SystemUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User principal = (User) authentication.getPrincipal();
        return userRepository.findByLogin(principal.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Current user not found"));
    }

    public List<SystemUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteUser(String login) {
        final var user = userRepository.findByLogin(login);
        if (user.isPresent()) {
            userRepository.deleteById(user.get().getLogin());
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        SystemUser user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " not found"));

        return new User(
                user.getLogin(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }

}
