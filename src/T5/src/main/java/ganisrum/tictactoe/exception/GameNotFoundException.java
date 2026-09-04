package ganisrum.tictactoe.exception;

import java.util.UUID;

public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String message) {
        super(message);
    }

    public GameNotFoundException(UUID id) {
        super("Game not found with id: " + id);
    }

}
