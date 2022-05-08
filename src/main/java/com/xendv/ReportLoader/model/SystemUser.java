package com.xendv.ReportLoader.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Entity(name = "user")
@Table(schema = "security", name = "user")
public class SystemUser implements UserDetails {
    @Id
    @Column(nullable = false)
    private @NotNull String login;
    private @NotNull String password;
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @NotNull Integer id;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
