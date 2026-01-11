import { HttpInterceptorFn } from '@angular/common/http';
import { keycloak, isKeycloakEnabled } from './keycloak.config';

/**
 * HTTP interceptor that attaches the Keycloak JWT token to all outgoing requests.
 * Only adds token if Keycloak is enabled and user is authenticated.
 */
export const authInterceptor: HttpInterceptorFn = (req, next) => {
    // Only add token if Keycloak is enabled and we have a token
    if (isKeycloakEnabled && keycloak?.token) {
        const authReq = req.clone({
            setHeaders: {
                Authorization: `Bearer ${keycloak.token}`
            }
        });
        return next(authReq);
    }

    return next(req);
};
