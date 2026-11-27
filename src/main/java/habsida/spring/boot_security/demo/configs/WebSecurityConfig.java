package habsida.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // 1. Most specific rules first: Restrict /admin and all subpaths to ADMIN
                        .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                        // 2. Allow both USER and ADMIN to access /user and its subpaths
                        .requestMatchers("/user", "/user/**").hasAnyRole("USER", "ADMIN")
                        // 3. All other endpoints require authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                    .successHandler(successUserHandler)
                    .permitAll()
                )
                .logout(logout -> logout.permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
