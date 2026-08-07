package com.linkandcelebrate.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Configurar soporte de CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. Deshabilitar CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // 3. Manejo de sesiones sin estado
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. Reglas de autorización de endpoints

                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers("/api/public/**", "/api/v1/invitations/**", "/api/v1/rsvp/**").permitAll()

                        // Endpoints del Plan VIP
                        .requestMatchers("/api/v1/vip/**").permitAll()

                        // Cualquier otra ruta requiere autenticación por defecto
                        .anyRequest().permitAll() // Cambiar a .authenticated() cuando integres JWT
                );

        return http.build();
    }

    // Configuración completa de CORS para permitir peticiones desde el Frontend
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Dominios permitidos (Localhost y tu frontend en producción)
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:3000",
                "http://localhost:5173",
                "http://127.0.0.1:5500",
                "https://*.netlify.app" // Para tus despliegues de invitaciones
        ));

        // Métodos HTTP soportados por la API
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // Cabeceras permitidas
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));

        // Permitir envío de credenciales o cookies si fuera necesario
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}