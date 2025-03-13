package com.dan.sharelib.util;

import com.dan.sharelib.enums.Message;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Component
@Slf4j
public class JwtUtil {

    @Value("${config.security.jwt.issuer:yourIssuer}")
    private String issuer;

    @Value("${config.security.jwt.subject:yourSubject}")
    private String subject;

    @Value("${config.security.jwt.secret:thisIsSecretForJwt}")
    private String jwtSecret;

    @Value("${config.security.jwt.expireInHours:24}")
    private Long jwtExpireInHours;


    public String generateToken(Map<String, Object> claims) {
        try {
            return Jwts.builder()
                    .issuer(issuer)
                    .subject(subject)
                    .claims(claims)
                    .issuedAt(Date.from(Instant.now()))
                    .expiration(Date.from(Instant.now().plus(TimeUnit.HOURS.toMillis(jwtExpireInHours), ChronoUnit.MILLIS)))
                    .signWith(getSecretKey())
                    .compact();
        } catch (JwtException ex) {
            log.error("error generate jwt token = {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Message.SOMETHING_WENT_WRONG.getMsg());
        }
    }

    public String getClaims(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("username").toString();
        } catch (JwtException ex) {
            log.error("error decode jwt token = {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Message.SOMETHING_WENT_WRONG.getMsg());
        }
    }

    public SecretKey getSecretKey() {
        String key = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    public boolean validToken(String token) {
        try {
            SecretKey key = getSecretKey();
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch(SecurityException | MalformedJwtException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "JWT was expired or incorrect");
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "JWT token compact of handler are invalid.");
        }
    }



}
