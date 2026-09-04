package ganisrum.tictactoe.datasource.repository;

import ganisrum.tictactoe.datasource.mapper.GameMapper;
import ganisrum.tictactoe.datasource.model.GameStatus;
import ganisrum.tictactoe.domain.model.Game;
import ganisrum.tictactoe.domain.model.LiderBoard;
import ganisrum.tictactoe.domain.repositoty.DomainGameRepository;
import ganisrum.tictactoe.exception.GameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DomainGameRepositoryImpl implements DomainGameRepository {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public DomainGameRepositoryImpl(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    @Override
    public void saveGame(Game game) {
        if (game == null) {
            throw new GameNotFoundException("nothing to save");
        }
        gameRepository.save(gameMapper.toEntity(game));
    }

    @Override
    public Game getGameById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Game id cannot be null");
        }
        return gameMapper.toDomain(gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id)));

    }


    @Override
    public List<UUID> findAvailableGamesIdInStorage(UUID id) {
        return gameRepository.findAvailableGamesIdInStorage(GameStatus.WAITING_PLAYERS, id);
    }


    @Override
    public List<UUID> getAllGameIds() {
        return gameRepository.getAllGameIds(GameStatus.FINISHED_STATES);
    }

    @Override
    public List<Game> getGameHistoryByUserId(UUID id) {
        return gameRepository.getFinishedGamesByUserId(id, GameStatus.FINISHED_STATES).stream()
                .map(game -> gameMapper.toDomain(game))
                .toList();
    }

    @Override
    public List<LiderBoard> getLiderBoard(int limit) {
        return gameRepository.getLiderBoard(limit);
    }
}
