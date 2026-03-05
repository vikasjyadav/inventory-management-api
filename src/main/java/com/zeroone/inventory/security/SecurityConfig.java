package com.zeroone.inventory.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth

                        // Public endpoints
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/register").permitAll()
                        .requestMatchers("/error").permitAll()

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Orders
                        .requestMatchers(HttpMethod.POST, "/orders")
                        .hasAnyRole("STAFF", "MANAGER")

                        .requestMatchers(HttpMethod.GET, "/orders/**")
                        .hasAnyRole("STAFF", "MANAGER" , "ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/orders/**")
                        .hasRole("MANAGER")

                        // Reports
                        .requestMatchers(HttpMethod.GET, "/reports/sales")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/reports/low-stock/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        // Product APIs
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/products")
                        .hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers(HttpMethod.PATCH, "/api/products/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers(HttpMethod.PUT, "/api/products/**")
                        .hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers( "/api/users/**")
                        .hasRole("ADMIN")

                        // Remaining endpoints
                        .anyRequest().authenticated()
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)

                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }
}