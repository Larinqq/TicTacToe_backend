package ganisrum.tictactoe.datasource.model;

import ganisrum.tictactoe.domain.model.Role;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "host", fetch = FetchType.LAZY)
    private final List<GameEntity> hostedGames = new ArrayList<>();

    @OneToMany(mappedBy = "guest", fetch = FetchType.LAZY)
    private final List<GameEntity> guestGames = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(String login, String password, Set<Role> roles) {
        this.id = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
