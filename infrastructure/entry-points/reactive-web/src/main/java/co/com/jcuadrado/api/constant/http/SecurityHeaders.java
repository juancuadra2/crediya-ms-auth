package co.com.jcuadrado.api.constant.http;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constantes para headers de seguridad HTTP
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityHeaders {
    
    // Content Security Policy
    public static final String CSP_NAME = "Content-Security-Policy";
    public static final String CSP_VALUE = "default-src 'self'; frame-ancestors 'self'; form-action 'self'";
    
    // Strict Transport Security
    public static final String STS_NAME = "Strict-Transport-Security";
    public static final String STS_VALUE = "max-age=31536000; includeSubDomains";
    
    // Content Type Options
    public static final String X_CONTENT_TYPE_OPTIONS_NAME = "X-Content-Type-Options";
    public static final String X_CONTENT_TYPE_OPTIONS_VALUE = "nosniff";
    
    // Referrer Policy
    public static final String REFERRER_POLICY_NAME = "Referrer-Policy";
    public static final String REFERRER_POLICY_VALUE = "strict-origin-when-cross-origin";
    
    // Server Header
    public static final String SERVER_NAME = "Server";
    public static final String SERVER_VALUE = "";
    
    // Cache Control
    public static final String CACHE_CONTROL_NAME = "Cache-Control";
    public static final String CACHE_CONTROL_VALUE = "no-store";
    
    // Pragma
    public static final String PRAGMA_NAME = "Pragma";
    public static final String PRAGMA_VALUE = "no-cache";
}
