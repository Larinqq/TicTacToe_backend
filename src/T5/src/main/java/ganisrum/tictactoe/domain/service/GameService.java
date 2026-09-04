package ganisrum.tictactoe.domain.service;

import ganisrum.tictactoe.domain.model.Game;
import ganisrum.tictactoe.domain.model.GameField;
import ganisrum.tictactoe.domain.model.LiderBoard;

import java.util.List;
import java.util.UUID;

public interface GameService {

    Game createNewGame(UUID id, boolean gameRequest, boolean isHostMarkX);

    Game makePlayerMove(UUID id, GameField gameFromWeb, UUID userId);

    Game getGameById(UUID id);

    Game joinGame(UUID gameId, UUID playerId);

    List<UUID> findAvailableGamesid(UUID id);

    List<UUID> getAllGamesIds();

    List<Game> getGamesHistoryByUserId(UUID id);

    List<LiderBoard> getLiderBoard(int limit);
}
