package com.SpringSecurity.SpringSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth->{
                   auth.requestMatchers("v1/saludo2").permitAll();
                   auth.anyRequest().authenticated();
                })
                .formLogin(form->{
                    form.successHandler(successHandler());
                    form.permitAll();
                })
                .sessionManagement(s-> {
                    s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                    s.invalidSessionUrl("/login");
                    s.maximumSessions(1).expiredUrl("/login")
                                        .sessionRegistry(sessionRegistry());
                    s.sessionFixation().migrateSession();
                })
                .build();
    }
    @Bean //Hace un rastreo de los datos del usuario que se logueo
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }
    public AuthenticationSuccessHandler successHandler(){
        return (((request, response, authentication) -> {
            response.sendRedirect("v1/public");
        }));
    }
}
