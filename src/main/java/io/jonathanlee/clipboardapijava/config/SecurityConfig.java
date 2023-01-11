package io.jonathanlee.clipboardapijava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers("/register").permitAll();
          auth.requestMatchers("/login").permitAll();
          auth.requestMatchers("/login-status/failure").permitAll();
          auth.requestMatchers("/register/confirm/**").permitAll();
          auth.anyRequest().authenticated();
        }).csrf().disable()
        .formLogin()
        .successForwardUrl("/login-status/success")
        .failureUrl("/login-status/failure")
        .and()
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
