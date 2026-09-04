package ganisrum.tictactoe.security;

import ganisrum.tictactoe.domain.model.Role;
import org.springframework.security.core.GrantedAuthority;

public record UserRole(Role role) implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return "ROLE_" + role.name();
    }

}
