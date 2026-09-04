package ganisrum.tictactoe.security;

import ganisrum.tictactoe.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtProvider {
    private final SecretKey accessKey;
    private final SecretKey refreshKey;
    private final Duration accessKeyDuration;
    private final Duration refreshKeyDuration;

    public JwtProvider(@Value("${jwt.access.secret}") String accessKey,
                       @Value("${jwt.refresh.secret}") String refreshKey,
                       @Value("${jwt.access.duration}") Duration accessKeyDuration,
                       @Value("${jwt.refresh.duration}") Duration refreshKeyDuration) {
        this.accessKey = Keys.hmacShaKeyFor(accessKey.getBytes(StandardCharsets.UTF_8));
        this.refreshKey = Keys.hmacShaKeyFor(refreshKey.getBytes(StandardCharsets.UTF_8));
        this.accessKeyDuration = accessKeyDuration;
        this.refreshKeyDuration = refreshKeyDuration;
    }

    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = user.getRoles().stream()
                .map(UserRole::getAuthority).toList();
        claims.put("id", user.getId().toString());
        claims.put("roles", roles);
        return generateToken(claims, accessKey, accessKeyDuration);
    }

    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId().toString());
        return generateToken(claims, refreshKey, refreshKeyDuration);
    }

    private String generateToken(Map<String, Object> claims, SecretKey key, Duration duration) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(duration)))
                .signWith(key)
                .compact();
    }

    private boolean validateToken(String token, SecretKey key) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token, accessKey);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, refreshKey);
    }

    private Claims getClaims(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public Claims getAccessClaims(String token) {
        return getClaims(token, accessKey);
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, refreshKey);
    }

}
