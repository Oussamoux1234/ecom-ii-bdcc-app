package ma.emsi.oussama.billingservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * Configuration for Feign clients to propagate JWT tokens in inter-service
 * calls.
 * Ensures that when billing-service calls customer-service or
 * inventory-service,
 * the original JWT token is forwarded.
 */
@Configuration
public class FeignClientConfig {

    /**
     * Interceptor that adds the JWT token from the current security context
     * to outgoing Feign requests.
     */
    @Bean
    public RequestInterceptor jwtRequestInterceptor() {
        return requestTemplate -> {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthenticationToken jwtAuth) {
                String token = jwtAuth.getToken().getTokenValue();
                requestTemplate.header("Authorization", "Bearer " + token);
                System.out.println("🔐 Feign: Added JWT token to request");
            }
        };
    }
}
