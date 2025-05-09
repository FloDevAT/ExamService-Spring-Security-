package at.htlkaindorf.examservice.security;

import at.htlkaindorf.examservice.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    /**
     * Provides a new bean for a "PasswordEncoder"
     * @return new bcrypt password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides a new bean for a "AuthenticationManager"
     * @param configuration injection required to create a manager bean
     * @return authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter authenticationFilter,
            UnauthorizedEntryPoint entryPoint
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // disable CSRF
                .exceptionHandling(e -> e.authenticationEntryPoint(entryPoint)) // register an entry point that gets returned on errors
                .authorizeHttpRequests(reg -> {
                    reg.requestMatchers("public/**").permitAll(); // allow all users on public/** routes
                    reg.requestMatchers("exams/**").hasRole("USER"); // check for role (auth) in exams/** routes
                    reg.anyRequest().permitAll(); // allow all other requests :)
                })
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class) // register the JWT Authentication filter
                .build(); // build the chain
    }

}
