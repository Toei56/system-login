package com.example.login.config;

import com.example.login.config.token.TokenFilter;
import com.example.login.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final TokenService tokenService;

    private final String[] PUBLIC = {
            "/test/**",
            "/actuator/**",
            "/auth/register",
            "/auth/login",
            "/auth/activate",
            "/auth/resend-activation-email"
    };

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    private TokenFilter tokenFilter() {
        return new TokenFilter(tokenService);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(config -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowCredentials(true);
                    cors.addAllowedOrigin("http://localhost:3000");
                    cors.addAllowedHeader("*");
                    cors.addAllowedMethod("OPTION");
                    cors.addAllowedMethod("GET");
                    cors.addAllowedMethod("POST");
                    cors.addAllowedMethod("PUT");
                    cors.addAllowedMethod("DELETE");

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", cors);
                    config.configurationSource(source);
                })
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz.requestMatchers(PUBLIC).permitAll().anyRequest().authenticated())
                .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(
                                (req, res, err) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                        ));
        return http.build();
    }

}
