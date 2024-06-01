package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;

import java.util.UUID;

@EnableWebSecurity
@Component
public class SecurityConfig {

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // authentication
        String password = UUID.randomUUID().toString();
        System.out.println("User Pasword mazgi: " + password);

        UserDetails user = User.builder()
                .username("user")
                .password("{noop}" + password)
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}adminjon")
                .roles("ADMIN")
                .build();

        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager(user, admin));
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers("/auth/**").permitAll()
                    // TODO PROFILE
                    .requestMatchers("/profile/adm/**").hasRole("ADMIN")
                    .requestMatchers("/profile/updateAny").permitAll()
                    .requestMatchers("/profile/filter").permitAll()
                    //  TODO TYPES
                    .requestMatchers("/types/adm/**").hasRole("ADMIN")
                    .requestMatchers("/types/any/**").permitAll()
//                    // TODO REGION
//                    .requestMatchers("/region/adm/**").hasRole("ADMIN")
//                    .requestMatchers("/region/any/**").permitAll()
                    // TODO CATEGORY
                    .requestMatchers("/category/adm/**").hasRole("ADMIN")
                    .requestMatchers("/category/any/**").permitAll()

                    .requestMatchers("/region/lang").permitAll()
                    .requestMatchers("/region/adm/**").hasRole("ADMIN")
                    .anyRequest()
                    .authenticated();
        });
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        return http.build();
    }


}
