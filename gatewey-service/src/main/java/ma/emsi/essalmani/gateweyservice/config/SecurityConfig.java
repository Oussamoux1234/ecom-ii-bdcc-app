package ma.emsi.essalmani.gateweyservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

        @Value("${security.enabled:false}")
        private boolean securityEnabled;

        @Bean
        public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
                http.csrf(csrf -> csrf.disable());

                if (securityEnabled) {
                        http
                                        .authorizeExchange(exchanges -> exchanges
                                                        .pathMatchers("/actuator/**").permitAll()
                                                        .pathMatchers("/api/**").authenticated()
                                                        .pathMatchers("/bills/**").authenticated()
                                                        .anyExchange().authenticated())
                                        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
                                        }));
                } else {
                        http.authorizeExchange(exchanges -> exchanges.anyExchange().permitAll());
                        System.out.println(
                                        "⚠️  Gateway Security is DISABLED. Set security.enabled=true for production.");
                }

                return http.build();
        }
}
