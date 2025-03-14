package com.dan.sharelib.util;

import com.dan.sharelib.enums.Message;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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
                    .expiration(Date.from(Instant.now().plus(TimeUnit.HOURS.toHours(jwtExpireInHours), ChronoUnit.HOURS)))
                    .signWith(getSecretKey())
                    .compact();
        } catch (JwtException ex) {
            log.error("error generate jwt token = {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Message.SOMETHING_WENT_WRONG.getMsg());
        }
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
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
            return ObjectUtils.isNotEmpty(Jwts.parser().verifyWith(key).build().parseSignedClaims(token));
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

    public Integer getExpiryInSeconds(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            long iatSeconds = claims.getIssuedAt().getTime() / 1000;
            long expSeconds = claims.getExpiration().getTime() / 1000;
            Date iatDate = new Date(iatSeconds * 1000);
            Date expDate = new Date(expSeconds * 1000);
            Long hoursGap = (expDate.getTime() - iatDate.getTime()) / (1000 * 60 * 60);
            return hoursGap.intValue() * 60 * 60;
        } catch (JwtException ex) {
            log.error("error decode jwt token = {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Message.SOMETHING_WENT_WRONG.getMsg());
        }
    }

}
