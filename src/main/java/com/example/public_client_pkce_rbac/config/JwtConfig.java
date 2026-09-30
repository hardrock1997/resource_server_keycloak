package com.example.public_client_pkce_rbac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
public class JwtConfig {

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {

        KeycloakJwtGrantedAuthoritiesConverter
                authoritiesConverter =
                new KeycloakJwtGrantedAuthoritiesConverter();

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                authoritiesConverter
        );

        return converter;
    }
}