package com.example.springessentials.config;

import static com.example.springessentials.config.SecurityConstants.SIGN_UP_URL;

import com.example.springessentials.model.service.CustomUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired CustomUsersService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, SIGN_UP_URL)
                .permitAll()
                .antMatchers("/*/protected/**")
                .hasRole("USER")
                .antMatchers("/*/admin/**")
                .hasRole("ADMIN")
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), service));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
    }

    //    @Override
    //    protected void configure(HttpSecurity http) throws Exception {
    //        http.authorizeRequests()
    //                .antMatchers("/*/protected/**")
    //                .hasRole("USER")
    //                .antMatchers("/*/admin/**")
    //                .hasRole("ADMIN")
    //                .and()
    //                .httpBasic()
    //                .and()
    //                .csrf()
    //                .disable();
    //    }
}
