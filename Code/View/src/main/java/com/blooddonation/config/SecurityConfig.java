package com.blooddonation.config;

import com.blooddonation.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthService authService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authService);
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
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**", "/", "/login", "/register", "/error").permitAll()
                .requestMatchers("/donors", "/doctors", "/bloodbanks", "/inventory", "/patients", "/events").permitAll()
                .requestMatchers("/dashboard/doctor/**", "/dashboard/doctor").hasAuthority("Doctor")
                .requestMatchers("/dashboard/patient/**", "/dashboard/patient").hasAuthority("Patient")
                .requestMatchers("/dashboard/donor/**", "/dashboard/donor").hasAuthority("Donor")
                .requestMatchers("/dashboard/bloodbank/**", "/dashboard/bloodbank").hasAuthority("BloodBank")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")  // Use 'email' instead of default 'username'
                .passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler())
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("none");

            switch (role) {
                case "Doctor":
                    response.sendRedirect("/dashboard/doctor");
                    break;
                case "Patient":
                    response.sendRedirect("/dashboard/patient");
                    break;
                case "Donor":
                    response.sendRedirect("/dashboard/donor");
                    break;
                case "BloodBank":
                    response.sendRedirect("/dashboard/bloodbank");
                    break;
                default:
                    response.sendRedirect("/login?error");
            }
        };
    }
}

