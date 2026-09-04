package ganisrum.tictactoe.security;

import ganisrum.tictactoe.domain.model.Role;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    public JwtUtil() {
    }

    public JwtAuthentication createJwtAuthentication(Claims claims) {
        UUID id = UUID.fromString(claims.get("id", String.class));
        List<String> rolesList = claims.get("roles", List.class);
        Set<UserRole> roles = rolesList != null ?
                rolesList.stream()
                        .map(name -> new UserRole(Role.valueOf(name.substring("ROLE_".length()))))
                        .collect(Collectors.toSet())
                : new HashSet<>();

        return new JwtAuthentication(id, roles, true);
    }
}
