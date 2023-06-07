package com.steppedua.securityproject.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(@NonNull HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/user").hasAnyAuthority("USER")
//                        .requestMatchers("/api/v1/admin").hasAnyAuthority("ADMIN")
                                .requestMatchers("/api/anonymous/**").permitAll()
                                .anyRequest().authenticated()

                )
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }


    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }


    public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter delegate = new JwtGrantedAuthoritiesConverter();

        return jwt -> {
            Collection<GrantedAuthority> grantedAuthorities = delegate.convert(jwt);

            if (jwt.getClaim("realm_access") == null) {
                return grantedAuthorities;
            }

            Map<String, ArrayList<String>> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess.get("roles") == null) {
                return grantedAuthorities;
            }

            ArrayList<String> roles = realmAccess.get("roles");

            final List<SimpleGrantedAuthority> keycloakAuthorities = Stream.of(roles)
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .toList();
            grantedAuthorities.addAll(keycloakAuthorities);

            return grantedAuthorities;
        };
    }
}