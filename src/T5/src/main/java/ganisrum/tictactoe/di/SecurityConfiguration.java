package ganisrum.tictactoe.di;

import ganisrum.tictactoe.security.AuthFilter;
import ganisrum.tictactoe.security.JwtProvider;
import ganisrum.tictactoe.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public AuthFilter authFilter(JwtProvider jwtProvider,
                                 JwtUtil jwtUtil) {
        return new AuthFilter(jwtProvider, jwtUtil);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthFilter authFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/signup", "/auth/signin", "/auth/update-access", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
