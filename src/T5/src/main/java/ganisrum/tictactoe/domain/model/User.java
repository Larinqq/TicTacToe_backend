package ganisrum.tictactoe.domain.model;


import ganisrum.tictactoe.security.UserRole;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User {

    private UUID id;
    private String login;
    private String password;
    private Set<UserRole> roles = new HashSet<>();


    public User() {
        this.id = UUID.randomUUID();
    }

    public User(String login, String password, Set<UserRole> roles) {
        this.id = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.roles = roles;

    }

    public void addRole(Role role) {
        this.roles.add(new UserRole(role));
    }

    public Set<UserRole> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
