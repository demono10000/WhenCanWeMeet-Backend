package pl.demono10000.whencanwemeetbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.demono10000.whencanwemeetbackend.repository.UserRepository;
import pl.demono10000.whencanwemeetbackend.security.UserPrincipal;
import pl.demono10000.whencanwemeetbackend.service.CustomUserDetailsService;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    @Order(100)
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/api/login") // URL, gdzie Spring Security będzie przechwytywał dane logowania
                        .successHandler(this::loginSuccessHandler) // Handler do sukcesów
                        .failureHandler(this::loginFailureHandler) // Handler do porażek
                        .usernameParameter("username") // nazwa parametru username
                        .passwordParameter("password") // nazwa parametru password
                );
        http.cors(Customizer.withDefaults());
        return http.build();
    }

    private void loginSuccessHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        String token = jwtTokenProvider.generateToken(authentication);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectNode responseBody = new ObjectMapper().createObjectNode();
        responseBody.put("message", "Login success");
        responseBody.put("token", token);
        // send username
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        responseBody.put("username", userPrincipal.getUsername());
        responseBody.put("id", userPrincipal.getId());

        response.getWriter().write(responseBody.toString());

        System.out.println("Login success");
    }


    private void loginFailureHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        System.out.println("Login failure " + e.getMessage());
    }
}
