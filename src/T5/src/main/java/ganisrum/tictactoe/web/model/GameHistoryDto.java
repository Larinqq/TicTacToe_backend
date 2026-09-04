package ganisrum.tictactoe.web.model;

import java.time.Instant;
import java.util.UUID;

public record GameHistoryDto(
        UUID gameId,
        UUID hostId,
        UUID guestId,
        GameStatus status,
        boolean vsComputer,
        Instant createdAt
) {

    public GameHistoryDto(UUID gameId, UUID hostId, GameStatus status,
                          boolean vsComputer, Instant createdAt) {
        this(gameId, hostId, null, status, vsComputer, createdAt);
    }
}