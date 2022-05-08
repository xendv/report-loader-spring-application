package com.xendv.ReportLoader.model;

import lombok.Data;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class SystemUser{
    @Id
    @Column(nullable = false)
    private @NotNull String login;
    private @NotNull String password;
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @NotNull Integer id;
}
