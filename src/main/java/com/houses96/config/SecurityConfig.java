package com.houses96.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/state/**").permitAll() // Disable security for this endpoint
                .antMatchers("/cities/**").permitAll() // Disable security for this endpoint
                .and()
                .csrf().disable(); // Also consider disabling CSRF for testing
    }
}







