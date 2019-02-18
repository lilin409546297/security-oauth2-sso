package com.cetc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * security配置不多解释
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //加密
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //验证
    @Autowired
    private AuthDetailsService authDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //过滤配置，根据自己的需求进行配置
        http
            .csrf().disable()
            .exceptionHandling()
        .and()
            .authorizeRequests()
            .antMatchers("/authorize/login").permitAll()
            .anyRequest().authenticated()
        .and()
            .formLogin();
    }

    /**
     * 验证配置
     * @param builder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(authDetailsService).passwordEncoder(passwordEncoder);
    }
}
