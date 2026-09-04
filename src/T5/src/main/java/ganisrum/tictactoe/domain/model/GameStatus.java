package ganisrum.tictactoe.domain.model;

import java.util.List;

public enum GameStatus {
    WAITING_PLAYERS, HOST_TURN, GUEST_TURN, DRAW, WON_HOST, WON_GUEST;

    public static final List<GameStatus> FINISHED_STATES = List.of(DRAW, WON_HOST, WON_GUEST);

    public boolean isGameOver() {
        return FINISHED_STATES.contains(this);
    }
}


