package com.example.naitei19javaecommerce.config;

import com.example.naitei19javaecommerce.service.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors().and()
//                .cors(cors -> {
//                    cors.configurationSource(corsConfigurationSource());
//                })
                .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/home/**", "/shop/**", "/login/**","/filter/**","/payment/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("0")
                .requestMatchers("/cart/**").hasAuthority("1")
                .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login").loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new CustomAuthenticationSuccessHandler()))
//                .defaultSuccessUrl("/home", true))

                .logout(out -> out
                        .logoutUrl("/logout").logoutSuccessUrl("/login")
                        .invalidateHttpSession(true) // Invalidate all sessions after logout
                        .deleteCookies("JSESSIONID")
//                        .addLogoutHandler(new CustomLogoutHandler(this.redisIndexedSessionRepository))
//                        .logoutSuccessHandler((request, response, authentication) ->
//                                SecurityContextHolder.clearContext()
                );
        return http.build();
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Cho phép truy cập từ tất cả các nguồn gốc
        config.addAllowedHeader("*"); // Cho phép tất cả các tiêu đề
        config.addAllowedMethod("*"); // Cho phép tất cả các phương thức HTTP
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/static/**", "/assets/**");
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(this.passwordEncoder);
        provider.setUserDetailsService(this.customUserDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}