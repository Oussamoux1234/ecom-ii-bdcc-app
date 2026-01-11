package ma.emsi.agentbot.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Client for accessing backend microservices through the API Gateway.
 * All requests are authenticated using Keycloak client credentials.
 */
@Service
public class BackendApiClient {

    private final RestClient restClient;
    private final KeycloakTokenService tokenService;

    public BackendApiClient(
            @Value("${backend.gateway.url}") String gatewayUrl,
            KeycloakTokenService tokenService) {
        this.tokenService = tokenService;
        this.restClient = RestClient.builder()
                .baseUrl(gatewayUrl)
                .build();
    }

    /**
     * Get all customers from customer-service via Gateway.
     */
    public String getCustomers() {
        return executeGet("/api/customers");
    }

    /**
     * Get a specific customer by ID.
     */
    public String getCustomerById(Long id) {
        return executeGet("/api/customers/" + id);
    }

    /**
     * Get all products from inventory-service via Gateway.
     */
    public String getProducts() {
        return executeGet("/api/products");
    }

    /**
     * Get a specific product by ID.
     */
    public String getProductById(String id) {
        return executeGet("/api/products/" + id);
    }

    /**
     * Get all bills from billing-service via Gateway.
     */
    public String getBills() {
        return executeGet("/api/bills");
    }

    /**
     * Get bill details including customer and product info.
     */
    public String getBillDetails(Long id) {
        return executeGet("/bills/" + id);
    }

    /**
     * Execute a GET request with Keycloak authentication.
     */
    private String executeGet(String uri) {
        String token = tokenService.getAccessToken();
        try {
            return restClient.get()
                    .uri(uri)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            System.err.println("❌ API call failed: " + uri + " - " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }
}
