package ee.nutikas.games.api.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.nutikas.games.api.auth.dto.UserPoints;
import ee.nutikas.games.engine.level.LevelSystem;
import ee.nutikas.games.api.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.refresh-expiration}")
    private int refreshExpiration;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LevelSystem levelSystem = new LevelSystem();

    public JwtUtils() {
        objectMapper.findAndRegisterModules();
    }

    public String[] generateJwtTokens(Authentication authentication) throws JsonProcessingException {
        var userPrincipal = ((UserDetailsImpl)authentication.getPrincipal());
        var nickname = ((UserDetailsImpl)authentication.getPrincipal()).getNickname();
        var roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        var userPoints = new UserPoints();
        userPoints.setCoins(userPrincipal.getCoins());
        userPoints.setExperience(userPrincipal.getExperience() != null ? userPrincipal.getExperience() : 0);
        userPoints.setLevel(levelSystem.getLevelForExperience(userPrincipal.getExperience()));
        userPoints.setStartingXp(levelSystem.getExperienceRequiredForNextLevel(userPoints.getLevel() - 1));
        userPoints.setExperienceLeft(levelSystem.getExperienceRequiredForNextLevel(userPoints.getLevel()));
        var keyBytes = Decoders.BASE64.decode(secret);
        var key = Keys.hmacShaKeyFor(keyBytes);
        var accessToken = Jwts.builder()
                .setSubject(nickname)
                .claim("authorities", roles)
                .claim("points", objectMapper.writeValueAsString(userPoints))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(key)
                .compact();

        var refreshToken = Jwts.builder()
                .setSubject(nickname)
                .claim("authorities", roles)
                .claim("points", objectMapper.writeValueAsString(userPoints))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + refreshExpiration))
                .signWith(key)
                .compact();

        return new String[] {accessToken, refreshToken};
    }

    public String getUserNameFromJwtToken(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }

    public boolean validateJwtToken(String authToken) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
