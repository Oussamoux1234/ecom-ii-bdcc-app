package ma.emsi.essalmani.gateweyservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * CORS configuration for the Gateway.
 * Allows Angular frontend to make cross-origin requests.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Allow all origins during development
        // For production, replace with specific origins
        corsConfig.setAllowedOriginPatterns(List.of("*"));

        // Allow all common HTTP methods
        corsConfig.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));

        // Allow all headers
        corsConfig.setAllowedHeaders(List.of("*"));

        // Expose headers for the client
        corsConfig.setExposedHeaders(List.of("*"));

        // Allow credentials (cookies, auth headers)
        corsConfig.setAllowCredentials(true);

        // Cache preflight response for 1 hour
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        System.out.println("✅ CORS filter configured - allowing all origins");

        return new CorsWebFilter(source);
    }
}
