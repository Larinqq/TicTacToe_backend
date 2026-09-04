package ganisrum.tictactoe.domain.repositoty;

import ganisrum.tictactoe.domain.model.Game;
import ganisrum.tictactoe.domain.model.LiderBoard;

import java.util.List;
import java.util.UUID;

public interface DomainGameRepository {
    Game getGameById(UUID id);

    void saveGame(Game game);

    List<UUID> findAvailableGamesIdInStorage(UUID id);

    List<UUID> getAllGameIds();

    List<Game> getGameHistoryByUserId(UUID id);

    List<LiderBoard> getLiderBoard(int limit);

}
