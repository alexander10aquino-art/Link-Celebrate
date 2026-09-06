package com.linkandcelebrate.backend.config;

import com.linkandcelebrate.backend.service.UsuariosServiceImplements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Lazy
    private UsuariosServiceImplements usuariosServiceImplements;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(usuariosServiceImplements);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // HABILITA CORS PARA QUE NETLIFY PUEDA LEER LA API
                .cors(org.springframework.security.config.Customizer.withDefaults())

                // EXCEPCIÓN DE SEGURIDAD CSRF PARA LA API (Permite el POST de confirmación desde Netlify)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/invitados/**"))

                .authorizeHttpRequests(auth -> auth
                        // 1. RUTAS PÚBLICAS
                        .requestMatchers(
                                "/",
                                "/catalogo",
                                "/plantillas",
                                "/plantillas-catalogo",
                                "/ver-catalogo",
                                "/registro",
                                "/invitacion/demo/**",
                                "/error",
                                "/*.css",
                                "/*.js",
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/api/invitados/**" // <-- API LIBERADA PARA LOS INVITADOS Y PLANTILLAS
                        ).permitAll()
                        // 2. RUTAS PROTEGIDAS (Botón "Elegir", "Dashboard", etc.)
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/catalogo", true) // <-- REDIRECCIÓN CORREGIDA
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );

        return http.build();
    }
}