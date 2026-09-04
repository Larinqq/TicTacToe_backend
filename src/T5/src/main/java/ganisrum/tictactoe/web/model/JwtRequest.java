package ganisrum.tictactoe.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class JwtRequest {
    @NotBlank(message = "Login cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]{2,10}$", message = "Login can only contains letters and numbers")
    private String login;

    @NotBlank(message = "Password cannot be empty")
    private String password;

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
