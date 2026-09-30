package com.example.public_client_pkce_rbac.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class KeycloakJwtGrantedAuthoritiesConverter
        implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        List<GrantedAuthority> authorities =
                new ArrayList<>();

        /*
         * --------------------------------------------------
         * 1. REALM ROLES
         *
         * Keycloak:
         *
         * "realm_access": {
         *     "roles": [
         *         "admin",
         *         "user"
         *     ]
         * }
         * --------------------------------------------------
         */

        Map<String, Object> realmAccess =
                jwt.getClaim("realm_access");

        if (realmAccess != null) {

            Object rolesObject =
                    realmAccess.get("roles");

            if (rolesObject instanceof List<?> roles) {

                for (Object roleObject : roles) {

                    String role =
                            roleObject.toString();

                    authorities.add(
                            new SimpleGrantedAuthority(
                                    "ROLE_" + role
                            )
                    );
                }
            }
        }

        /*
         * --------------------------------------------------
         * 2. CLIENT ROLES
         *
         * Keycloak:
         *
         * "resource_access": {
         *     "public_pkce_client": {
         *         "roles": [
         *             "admin",
         *             "user"
         *         ]
         *     }
         * }
         * --------------------------------------------------
         */

        Map<String, Object> resourceAccess =
                jwt.getClaim("resource_access");

        if (resourceAccess != null) {

            for (Map.Entry<String, Object> clientEntry
                    : resourceAccess.entrySet()) {

                String clientId =
                        clientEntry.getKey();

                Object clientAccessObject =
                        clientEntry.getValue();

                if (clientAccessObject instanceof Map<?, ?> clientAccess) {

                    Object rolesObject =
                            clientAccess.get("roles");

                    if (rolesObject instanceof List<?> roles) {

                        for (Object roleObject : roles) {

                            String role =
                                    roleObject.toString();

                            authorities.add(
                                    new SimpleGrantedAuthority(
                                            "ROLE_"
                                                    + clientId
                                                    + "_"
                                                    + role
                                    )
                            );
                        }
                    }
                }
            }
        }

        return authorities;
    }
}