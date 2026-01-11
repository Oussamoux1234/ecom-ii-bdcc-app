package ma.emsi.agentbot.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

/**
 * Service for obtaining access tokens from Keycloak using client credentials
 * flow.
 * Used by the Telegram bot to authenticate with backend services.
 */
@Service
public class KeycloakTokenService {

    private final RestClient restClient;
    private final String clientId;
    private final String clientSecret;
    private String cachedToken;
    private long tokenExpiry;

    public KeycloakTokenService(
            @Value("${keycloak.auth-server-url}") String authUrl,
            @Value("${keycloak.realm}") String realm,
            @Value("${keycloak.client-id}") String clientId,
            @Value("${keycloak.client-secret}") String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.restClient = RestClient.builder()
                .baseUrl(authUrl + "/realms/" + realm + "/protocol/openid-connect/token")
                .build();
    }

    /**
     * Gets a valid access token, refreshing if necessary.
     * 
     * @return The access token string
     */
    public synchronized String getAccessToken() {
        // Return cached token if still valid (with 10 second buffer)
        if (cachedToken != null && System.currentTimeMillis() < tokenExpiry - 10000) {
            return cachedToken;
        }

        var formData = new LinkedMultiValueMap<String, String>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);

        @SuppressWarnings("unchecked")
        Map<String, Object> response = restClient.post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .body(Map.class);

        if (response != null) {
            cachedToken = (String) response.get("access_token");
            int expiresIn = (Integer) response.get("expires_in");
            tokenExpiry = System.currentTimeMillis() + (expiresIn * 1000L);
            System.out.println("🔑 Obtained new Keycloak access token (expires in " + expiresIn + "s)");
        }

        return cachedToken;
    }
}
