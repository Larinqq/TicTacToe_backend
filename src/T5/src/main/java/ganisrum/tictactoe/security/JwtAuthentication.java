package ganisrum.tictactoe.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class JwtAuthentication implements Authentication {
    private final UUID id;
    private final Set<UserRole> roles;
    private final boolean isAuthenticated;

    public JwtAuthentication(UUID id, Set<UserRole> roles, boolean isAuthenticated) {
        this.id = id;
        this.roles = Collections.unmodifiableSet(roles);
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public @Nullable Object getCredentials() {
        return null;
    }

    @Override
    public @Nullable Object getDetails() {
        return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return id;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return id.toString();
    }
}
