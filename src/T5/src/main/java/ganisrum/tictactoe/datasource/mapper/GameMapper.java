package ganisrum.tictactoe.datasource.mapper;

import ganisrum.tictactoe.datasource.model.GameEntity;
import ganisrum.tictactoe.datasource.model.GameStatus;
import ganisrum.tictactoe.domain.model.Game;
import ganisrum.tictactoe.domain.model.GameField;
import org.springframework.stereotype.Component;

import static ganisrum.tictactoe.datasource.mapper.UserMapper.userToDomain;
import static ganisrum.tictactoe.datasource.mapper.UserMapper.userToEntity;

@Component
public class GameMapper {

    public GameMapper() {
    }

    public GameEntity toEntity(Game game) {
        GameEntity entity = new GameEntity();
        entity.setHost(userToEntity(game.getHost()));
        entity.setVsComputer(game.isVsComputer());
        entity.setHostMark(game.getHostMark());
        entity.setGuestMark(game.getGuestMark());
        if (game.getGuest() != null) {
            entity.setGuest(userToEntity(game.getGuest()));
        }
        entity.setId(game.getId());
        entity.setGameStatus(GameStatus.valueOf(game.getGameStatus().name()));
        GameField domainField = game.getField();
        StringBuilder sb = new StringBuilder(9);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int cellValue = domainField.getCell(i, j);
                sb.append(cellValue);
            }
        }

        entity.setField(sb.toString());
        entity.setCreatedAt(game.getCreatedAt());
        return entity;
    }

    public Game toDomain(GameEntity entity) {
        Game game = new Game();
        game.setHost(userToDomain(entity.getHost()));
        game.setVsComputer(entity.isVsComputer());
        if (entity.getGuest() != null) {
            game.setGuest(userToDomain(entity.getGuest()));
        }
        game.setId(entity.getId());
        game.setGameStatus(ganisrum.tictactoe.domain.model.GameStatus.valueOf(entity.getGameStatus().name()));
        game.setHostMark(entity.getHostMark());
        game.setGuestMark(entity.getGuestMark());
        String entityField = entity.getField();
        GameField domainField = new GameField();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int cellValue = Character.getNumericValue(entityField.charAt(3 * i + j));
                domainField.setCell(i, j, cellValue);
            }
        }
        game.setField(domainField);
        game.setCreatedAt(entity.getCreatedAt());

        return game;
    }
}
