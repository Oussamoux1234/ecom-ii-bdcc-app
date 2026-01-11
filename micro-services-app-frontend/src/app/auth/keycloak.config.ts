import Keycloak from 'keycloak-js';

/**
 * Keycloak instance (may be null if Keycloak is not available)
 */
export let keycloak: Keycloak | null = null;

/**
 * Flag indicating if Keycloak is enabled and user is authenticated
 */
export let isKeycloakEnabled = false;

/**
 * Initialize Keycloak - skips if Keycloak server is not available.
 * @returns Promise<boolean> - true if initialization successful, false if skipped
 */
export async function initKeycloak(): Promise<boolean> {
    // Try to initialize Keycloak directly - if it fails, continue without auth
    try {
        keycloak = new Keycloak({
            url: 'http://localhost:8080',
            realm: 'ecom-realm',
            clientId: 'ecom-frontend'
        });

        const authenticated = await keycloak.init({
            onLoad: 'check-sso',  // Don't force login, just check if already logged in
            checkLoginIframe: false,
            silentCheckSsoRedirectUri: undefined
        });

        if (authenticated) {
            console.log('✅ Keycloak: User authenticated');
            isKeycloakEnabled = true;

            // Set up automatic token refresh
            setInterval(() => {
                keycloak?.updateToken(70).catch(() => {
                    console.error('❌ Failed to refresh token');
                });
            }, 60000);
        } else {
            console.log('ℹ️ Keycloak: User not authenticated, redirecting to login...');
            isKeycloakEnabled = true;
            keycloak.login();
        }

        return true;
    } catch (error) {
        console.warn('⚠️ Keycloak not available - running without authentication');
        isKeycloakEnabled = false;
        keycloak = null;
        return true; // Continue without Keycloak
    }
}

/**
 * Get the current access token for API calls.
 */
export function getToken(): string | undefined {
    return keycloak?.token;
}

/**
 * Check if user is authenticated.
 */
export function isAuthenticated(): boolean {
    return isKeycloakEnabled && (keycloak?.authenticated ?? false);
}

/**
 * Logout the current user.
 */
export function logout(): void {
    if (keycloak) {
        keycloak.logout({ redirectUri: window.location.origin });
    }
}
