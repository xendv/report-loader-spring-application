package com.xendv.ReportLoader.service;

import com.xendv.ReportLoader.model.SystemUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    SystemUser create(SystemUser user);

    SystemUser getCurrentUser();

    boolean deleteUser(String login);
}
