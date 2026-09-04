package ganisrum.tictactoe.web.model;

import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String login;

    public UserDTO() {
    }

    public UserDTO(UUID id, String login) {
        this.id = id;
        this.login = login;
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
}
