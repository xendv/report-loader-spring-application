package com.xendv.ReportLoader.conf;

import com.xendv.ReportLoader.handler.authentication.AuthenticationFailedHandler;
import com.xendv.ReportLoader.handler.authentication.AuthenticationSucceedHandler;
import com.xendv.ReportLoader.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userDetailsService;
    @Autowired
    private AuthenticationSucceedHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailedHandler authenticationFailureHandler;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* http.authorizeRequests()
                .antMatchers("*").permitAll()
                .anyRequest().authenticated()
                *//*.antMatchers("/login*").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll() //registration
                .anyRequest().authenticated()
                .and()
                .formLogin().loginProcessingUrl("/login")
                //.usernameParameter("login").passwordParameter("password")
                //.failureHandler(authenticationFailureHandler)
                //.successHandler(authenticationSuccessHandler)*//*
                .and()
                .csrf().disable()
                .sessionManagement()
                //.and()
                //.httpBasic()
        ;*/
        http.csrf().disable().authorizeRequests().anyRequest().permitAll();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

}
