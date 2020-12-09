package com.serdyukov.atmservice.security;

import com.serdyukov.atmservice.entity.Card;
import com.serdyukov.atmservice.exeption.JWTValidationExeption;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String AUTHORITIES = "AUTHORITIES";
    private static final SignatureAlgorithm ALGORITHM_HS512 = SignatureAlgorithm.HS512;

    @Value("${secret.key}")
    private String secretKey;

    @Value("${token.expiration.time}")
    private byte expirationTime;


    public String accessToken(Card card) {
        if (Objects.nonNull(card)) {
            Date date = Date.from(LocalDate.now().plusDays(expirationTime).atStartOfDay(ZoneId.systemDefault()).toInstant());
            return Jwts.builder()
                    .setSubject(card.getId().toString())
                    .claim(AUTHORITIES, "ROLE_USER")
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(date)
                    .signWith(ALGORITHM_HS512, secretKey)
                    .compact();
        }
        return null;
    }

    public String getLoginFromJwt(String token) {
        String correctToken = getTokenFromRequest(token);
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(correctToken)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        String token = getTokenFromRequest(authToken);
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            throw new JWTValidationExeption("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new JWTValidationExeption("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new JWTValidationExeption("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new JWTValidationExeption("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
            throw new JWTValidationExeption("IJWT claims string is empty");
        }
    }

    private String getTokenFromRequest(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

    public Long getCardIdFromRequest(HttpServletRequest request) {
        String token = getTokenFromRequest(request.getHeader(HttpHeaders.AUTHORIZATION));
        return Long.valueOf(getLoginFromJwt(token));
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return bearer;
    }
}
