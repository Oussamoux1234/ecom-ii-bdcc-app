package ma.emsi.customerservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Value("${security.enabled:false}")
    private boolean securityEnabled;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.frameOptions(fo -> fo.disable()));

        if (securityEnabled) {
            // Security enabled - require JWT authentication
            http
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/actuator/**", "/h2-console/**").permitAll()
                            .anyRequest().authenticated())
                    .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
                    }));
        } else {
            // Security disabled - permit all requests (development mode)
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            System.out.println("⚠️  Security is DISABLED. Set security.enabled=true for production.");
        }

        return http.build();
    }
}
