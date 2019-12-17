package com.overlook.security.config;

import com.overlook.security.domain.UserProfile;
import com.overlook.security.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Value("${overlook.security.jwtSecret}")
    private String jwtSecret;

    @Value("${overlook.security.jwtExpiration}")
    private Integer jwtExpiration;

    public String generateToken(Authentication authentication) {

        UserProfile userProfile = (UserProfile) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(userProfile.getUserId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String extractJwtFromRequest(HttpServletRequest request) throws InvalidTokenException {
        String bearerToken = request.getHeader("Authorization");
        final String TOKEN_PREFIX = "Bearer ";

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }

        throw new InvalidTokenException("Token extraction failed");
    }

    public UUID extractUserIdFromToken(String token) throws InvalidTokenException {

        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return UUID.fromString(claims.getSubject());
    }

    public void validateToken(String authToken) throws InvalidTokenException {

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        } catch (SignatureException ex) {
            throw new InvalidTokenException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new InvalidTokenException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new InvalidTokenException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new InvalidTokenException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new InvalidTokenException("JWT claims string is empty");
        }
    }


}
