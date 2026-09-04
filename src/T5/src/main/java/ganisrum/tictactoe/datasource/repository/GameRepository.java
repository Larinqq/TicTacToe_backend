package ganisrum.tictactoe.datasource.repository;

import ganisrum.tictactoe.datasource.model.GameEntity;
import ganisrum.tictactoe.datasource.model.GameStatus;
import ganisrum.tictactoe.domain.model.LiderBoard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, UUID> {

    @Query("select c.id from GameEntity c where c.gameStatus = :state and c.host.id != :playerId")
    List<UUID> findAvailableGamesIdInStorage(GameStatus state, UUID playerId);

    @Query("select c.id from GameEntity c where c.gameStatus not in :state")
    List<UUID> getAllGameIds(List<GameStatus> state);

    @Query("""
             select c from GameEntity c where c.gameStatus in :state and :playerId in (c.host.id, c.guest.id)
            """)
    List<GameEntity> getFinishedGamesByUserId(UUID playerId, List<GameStatus> state);

    @Query(value = """
                       WITH users_stats as (SELECT host_id AS user_id,
                                             CASE WHEN game_status = 'WON_HOST' THEN 1 ELSE 0 END AS is_win
                                      FROM games
                                      WHERE game_status IN ('WON_HOST', 'GUEST_WON', 'DRAW')
                                        AND host_id IS NOT NULL
            
                                      UNION ALL
            
                                      SELECT guest_id AS user_id,
                                             CASE WHEN game_status = 'GUEST_WON' THEN 1 ELSE 0 END AS is_win
                                      FROM games
                                      WHERE game_status IN ('WON_HOST', 'GUEST_WON', 'DRAW')
                                        AND guest_id IS NOT NULL)
                       SELECT user_id as userId, round(sum(is_win)::numeric / count(*) * 100, 2)::float8 as winRate
                       FROM users_stats
                       GROUP BY user_id
                       ORDER BY winRate DESC
                       LIMIT :limit
            """, nativeQuery = true)
    List<LiderBoard> getLiderBoard(int limit);
}


