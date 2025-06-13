package com.jasso.finance.finance.security;
import com.jasso.finance.finance.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;
    @Autowired
    public SecurityConfig(JwtAuthenticationFilter thefilter,AuthenticationProvider theAuthProvider){
        jwtAuthenticationFilter = thefilter;
        authProvider = theAuthProvider;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http)throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
        );
        http.sessionManagement(sessionManager ->
                sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authenticationProvider(authProvider);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
