package co.com.jcuadrado.api.security;

import co.com.jcuadrado.api.constant.auth.JwtConstants;
import co.com.jcuadrado.api.constant.error.LogMessages;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    private final String secret;
    private final Long expiration;

    public JwtProvider(@Value("${security.jwt.secret}") String secret,
                      @Value("${security.jwt.expiration}") Long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim(JwtConstants.ROLES_CLAIM, userDetails.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiration))
                .signWith(getKey(secret))
                .compact();
    }

    private SecretKey getKey(String secret) {
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(getKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validate(String token){
        try {
            Jwts.parser()
                    .verifyWith(getKey(secret))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return true;
        } catch (ExpiredJwtException e) {
            log.error(LogMessages.TOKEN_EXPIRED_LOG, e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error(LogMessages.TOKEN_UNSUPPORTED_LOG, e.getMessage());
        } catch (MalformedJwtException e) {
            log.error(LogMessages.TOKEN_MALFORMED_LOG, e.getMessage());
        } catch (SignatureException e) {
            log.error(LogMessages.BAD_SIGNATURE_LOG, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error(LogMessages.ILLEGAL_ARGS_LOG, e.getMessage());
        }
        return false;
    }

}
