package ganisrum.tictactoe.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(UUID id) {
        super("user with id " + id + " not found");
    }

}