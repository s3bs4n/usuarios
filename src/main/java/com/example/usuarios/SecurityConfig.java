package com.example.usuarios;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/usuarios/**").hasAnyRole("ADMIN", "CLIENTE") // ADMIN y CLIENTE pueden hacer GET
                .requestMatchers("/usuarios").hasRole("ADMIN") // ADMIN puede hacer POST, PUT, DELETE
                .requestMatchers("/usuarios/**").hasRole("ADMIN") // ADMIN tiene acceso completo a todos los endpoints de usuarios
                .requestMatchers("/usuarios").hasAnyRole("ADMIN", "CLIENTE") // ADMIN y CLIENTE pueden hacer GET
                .anyRequest().authenticated()
            )
            .httpBasic(); // Autenticación básica

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("sebaAdmin")
                .password("12345")
                .roles("ADMIN")
                .build();
        
        UserDetails cliente = User.withDefaultPasswordEncoder()
                .username("usuarioComun")
                .password("qwert")
                .roles("CLIENTE")
                .build();

        return new InMemoryUserDetailsManager(admin, cliente);
    }
}
