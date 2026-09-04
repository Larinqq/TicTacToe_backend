package ganisrum.tictactoe.exception;

public class NotYourTurnException extends RuntimeException {
    public NotYourTurnException(String message) {
        super(message);
    }
}
