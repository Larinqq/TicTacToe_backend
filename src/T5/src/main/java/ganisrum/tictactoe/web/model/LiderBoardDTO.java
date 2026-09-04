package ganisrum.tictactoe.web.model;

import java.util.UUID;

public record LiderBoardDTO(UUID userId, String login, double winRate) {
}
