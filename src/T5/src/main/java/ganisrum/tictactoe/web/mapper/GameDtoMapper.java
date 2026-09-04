package ganisrum.tictactoe.web.mapper;

import ganisrum.tictactoe.domain.model.Game;
import ganisrum.tictactoe.domain.model.GameField;
import ganisrum.tictactoe.domain.model.LiderBoard;
import ganisrum.tictactoe.web.model.GameDTO;
import ganisrum.tictactoe.web.model.GameFieldDTO;
import ganisrum.tictactoe.web.model.GameHistoryDto;
import ganisrum.tictactoe.web.model.GameStatus;
import ganisrum.tictactoe.web.model.LiderBoardDTO;

public class GameDtoMapper {

    public static GameDTO gameToDto(Game game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setId(game.getId());
        gameDTO.setGameStatus(GameStatus.valueOf(game.getGameStatus().name()));
        gameDTO.setGuestMark(game.getGuestMark());
        gameDTO.setHostMark(game.getHostMark());
        gameDTO.setVsComputer(game.isVsComputer());
        gameDTO.setHost(UserDtoMapper.userToDto(game.getHost()));
        gameDTO.setGuest(UserDtoMapper.userToDto(game.getGuest()));
        gameDTO.setCreatedAt(game.getCreatedAt());


        GameFieldDTO fieldDTO = new GameFieldDTO();
        int[][] cells = fieldDTO.getFieldCells();
        GameField domainField = game.getField();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = domainField.getCell(i, j);
            }
        }
        gameDTO.setField(fieldDTO);
        return gameDTO;
    }

    public static Game gameToDomain(GameDTO gameDTO) {
        Game game = new Game();
        game.setId(gameDTO.getId());
        game.setGameStatus(ganisrum.tictactoe.domain.model.GameStatus.valueOf(gameDTO.getGameStatus().name()));
        game.setHostMark(gameDTO.getHostMark());
        game.setGuestMark(gameDTO.getGuestMark());
        game.setVsComputer(gameDTO.isVsComputer());
        if (!game.isVsComputer()) {
            game.setGuest(UserDtoMapper.userToDomain(gameDTO.getGuest()));
        }
        game.setHost(UserDtoMapper.userToDomain(gameDTO.getHost()));
        GameField domainField = new GameField();
        GameFieldDTO fieldDTO = gameDTO.getField();
        int[][] cells = fieldDTO.getFieldCells();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                domainField.setCell(i, j, cells[i][j]);
            }
        }
        game.setField(domainField);
        game.setCreatedAt(gameDTO.getCreatedAt());
        return game;
    }

    public static GameFieldDTO fieldToDto(GameField gameField) {
        GameFieldDTO fieldDTO = new GameFieldDTO();
        int[][] cells = fieldDTO.getFieldCells();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = gameField.getCell(i, j);
            }
        }
        return fieldDTO;
    }

    public static GameField fieldToDomain(GameFieldDTO gameFieldDTO) {
        GameField domainField = new GameField();
        int[][] cells = gameFieldDTO.getFieldCells();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                domainField.setCell(i, j, cells[i][j]);
            }
        }
        return domainField;
    }


    public static GameHistoryDto gameHistoryToDto(Game game) {
        return new GameHistoryDto(
                game.getId(),
                game.getHost().getId(),
                game.isVsComputer() ? null : game.getGuest().getId(),
                GameStatus.valueOf(game.getGameStatus().name()),
                game.isVsComputer(),
                game.getCreatedAt()
        );
    }

    public static LiderBoardDTO liderBoardToDto(LiderBoard liderBoard, String login) {
        return new LiderBoardDTO(
                liderBoard.getUserid(),
                login,
                liderBoard.getWinRate()
        );
    }

}
