package ganisrum.tictactoe.domain.service;

import ganisrum.tictactoe.domain.model.Game;
import ganisrum.tictactoe.domain.model.GameField;
import ganisrum.tictactoe.domain.model.GameStatus;
import ganisrum.tictactoe.domain.model.LiderBoard;
import ganisrum.tictactoe.domain.model.User;
import ganisrum.tictactoe.domain.repositoty.DomainGameRepository;
import ganisrum.tictactoe.exception.IncorrectDataProvided;
import ganisrum.tictactoe.exception.IncorrectGameProvidedException;
import ganisrum.tictactoe.exception.IncorrectMoveProvidedException;
import ganisrum.tictactoe.exception.NoAccessException;
import ganisrum.tictactoe.exception.NotYourTurnException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static ganisrum.tictactoe.domain.model.GameStatus.GUEST_TURN;
import static ganisrum.tictactoe.domain.model.GameStatus.HOST_TURN;
import static ganisrum.tictactoe.domain.model.GameStatus.WAITING_PLAYERS;

@Service
public class GameServiceImpl implements GameService {
    private static final int EMPTY = 0;
    private static final int PLAYER_1_MARKER = 1;
    private static final int PLAYER_2_MARKER = 2;
    private final DomainGameRepository gameRepository;
    private final UserService userService;

    public GameServiceImpl(DomainGameRepository gameRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
    }


    private boolean validateBoard(Game gameFromServer, GameField playersField, int playerMark) {
        GameField serverField = gameFromServer.getField();
        int difCounter = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int serverCell = serverField.getCell(i, j);
                int playerCell = playersField.getCell(i, j);

                if (playerCell != serverCell) {
                    if (difCounter > 0 || playerCell != playerMark || serverCell != EMPTY) {
                        return false;
                    }
                    difCounter++;
                }
            }
        }
        return difCounter == 1;
    }


    private int getWinnerMark(GameField gameField) {

        for (int i = 0; i < 3; i++) {
            int cell1 = gameField.getCell(0, i);
            int cell2 = gameField.getCell(1, i);
            int cell3 = gameField.getCell(2, i);
            if (cell1 != EMPTY && cell1 == cell2 && cell2 == cell3) {
                return cell1;
            }
        }

        for (int i = 0; i < 3; i++) {
            int cell1 = gameField.getCell(i, 0);
            int cell2 = gameField.getCell(i, 1);
            int cell3 = gameField.getCell(i, 2);
            if (cell1 != EMPTY && cell1 == cell2 && cell2 == cell3) {
                return cell1;
            }
        }

        int topLeft = gameField.getCell(0, 0);
        int middle = gameField.getCell(1, 1);
        int bottomRight = gameField.getCell(2, 2);
        int topRight = gameField.getCell(0, 2);
        int bottomLeft = gameField.getCell(2, 0);

        if (topLeft == middle && middle == bottomRight && middle != EMPTY) {
            return middle;
        }

        if (topRight == middle && middle == bottomLeft && middle != EMPTY) {
            return middle;
        }

        return 0;
    }

    @Override
    public Game createNewGame(UUID hostId, boolean vsComputer, boolean hostMarkX) {
        Game game = new Game();
        game.setVsComputer(vsComputer);
        game.setHost(userService.findById(hostId));
        if (!hostMarkX) {
            game.switchMarks();
        }

        if (!game.isVsComputer()) {
            game.setGameStatus(WAITING_PLAYERS);
        }
        gameRepository.saveGame(game);
        return game;
    }


    private boolean isGameFinished(Game game) {
        GameField gameField = game.getField();

        int winner = getWinnerMark(gameField);

        if (winner == PLAYER_1_MARKER) {
            game.setGameStatus(GameStatus.WON_HOST);
            return true;
        } else if (winner == PLAYER_2_MARKER) {
            game.setGameStatus(GameStatus.WON_GUEST);
            return true;
        } else if (isBoardFull(gameField)) {
            game.setGameStatus(GameStatus.DRAW);
            return true;
        }
        return false;

    }


    private void writeMove(Game gameFromServer, GameField fieldFromWeb, int mark) {
        GameField fieldFromServer = gameFromServer.getField();


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int playerCell = fieldFromWeb.getCell(i, j);
                int cellFromServer = fieldFromServer.getCell(i, j);
                if (cellFromServer == EMPTY && playerCell == mark) {
                    fieldFromServer.setCell(i, j, mark);
                    return;
                }
            }
        }
    }

    public Game getGameById(UUID id) {
        return gameRepository.getGameById(id);
    }

    public Game joinGame(UUID gameId, UUID playerId) {
        Game game = gameRepository.getGameById(gameId);
        User user = userService.findById(playerId);
        validatePlayer(game, playerId);
        game.setGuest(user);
        game.setGameStatus(HOST_TURN);
        gameRepository.saveGame(game);
        return game;
    }

    private void validatePlayer(Game game, UUID playerId) {
        if (game.getHost().getId().equals(playerId)) {
            throw new IncorrectGameProvidedException("You cannot join your own game");
        }
        if (game.getGuest() != null) {
            throw new IncorrectGameProvidedException("You cannot join, someone has already joined");
        }
        if (!game.getGameStatus().equals(WAITING_PLAYERS)) {
            throw new IncorrectGameProvidedException("You cannot join, game has already started");
        }
    }

    @Override
    public Game makePlayerMove(UUID id, GameField fieldFromWeb, UUID userId) {

        User user = userService.findById(userId);
        Game gameFromStorage = gameRepository.getGameById(id);

        checkAccess(gameFromStorage, userId);

        if (!checkTurn(gameFromStorage, user)) {
            throw new NotYourTurnException("its not your turn!");
        }

        int currentPlayerMark = getCurrentPlayerMark(gameFromStorage);

        if (!validateBoard(gameFromStorage, fieldFromWeb, currentPlayerMark)) {
            throw new IncorrectMoveProvidedException("Invalid move or game field");
        }

        switchTurn(gameFromStorage);
        writeMove(gameFromStorage, fieldFromWeb, currentPlayerMark);

        if (isGameFinished(gameFromStorage)) {
            gameRepository.saveGame(gameFromStorage);
            return gameFromStorage;
        }

        if (gameFromStorage.isVsComputer()) {
            int[] compMove = getNextMove(gameFromStorage);
            if (compMove[0] != -1) {
                gameFromStorage.getField().setCell(compMove[0], compMove[1], PLAYER_2_MARKER);
                isGameFinished(gameFromStorage);
                switchTurn(gameFromStorage);
            }
        }

        gameRepository.saveGame(gameFromStorage);
        return gameFromStorage;

    }

    private boolean checkTurn(Game game, User user) {
        if (game.isVsComputer())
            return true;

        if (game.getGameStatus().equals(HOST_TURN)) {
            return game.getHost().getId().equals(user.getId());
        }

        if (game.getGameStatus().equals(GUEST_TURN))
            return game.getGuest().getId().equals(user.getId());

        return false;
    }

    private int getCurrentPlayerMark(Game game) {
        int mark = -1;
        if (game.isVsComputer()) mark = PLAYER_1_MARKER;
        if (game.getGameStatus().equals(HOST_TURN)) mark = PLAYER_1_MARKER;
        if (game.getGameStatus().equals(GUEST_TURN)) mark = PLAYER_2_MARKER;
        return mark;
    }

    private void switchTurn(Game game) {

        if (game.getGameStatus().equals(HOST_TURN)) {
            game.setGameStatus(GUEST_TURN);
            return;

        }
        if (game.getGameStatus().equals(GUEST_TURN)) {
            game.setGameStatus(HOST_TURN);
        }
    }

    private void checkAccess(Game game, UUID userId) {
        if (game.getGameStatus().isGameOver()) {
            throw new IncorrectMoveProvidedException("Game finished");
        }


        boolean isHost = userId.equals(game.getHost().getId());
        boolean isGuest = game.getGuest() != null && userId.equals(game.getGuest().getId());


        if (game.isVsComputer()) {
            if (!isHost) {
                throw new NoAccessException("You are not a participant of the game");
            }
            return;
        }

        if (!isHost && !isGuest) {
            throw new NoAccessException("You are not a participant of the game");
        }
    }

    @Override
    public List<UUID> findAvailableGamesid(UUID playerId) {
        return gameRepository.findAvailableGamesIdInStorage(playerId);
    }

    @Override
    public List<UUID> getAllGamesIds() {
        return gameRepository.getAllGameIds();
    }

    private int[] getNextMove(Game game) {
        GameField gameField = game.getField();
        int highScore = -10000;
        int[] bestCell = new int[]{-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField.getCell(i, j) == EMPTY) {
                    GameField fieldCopy = copyField(gameField);
                    fieldCopy.setCell(i, j, PLAYER_2_MARKER);

                    int score = miniMaxAlgo(fieldCopy, 0, false);

                    if (score > highScore) {
                        highScore = score;
                        bestCell[0] = i;
                        bestCell[1] = j;
                    }
                }
            }
        }
        return bestCell;
    }

    private GameField copyField(GameField original) {
        GameField copy = new GameField();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                copy.setCell(i, j, original.getCell(i, j));
            }
        }
        return copy;
    }


    private int miniMaxAlgo(GameField gameField, int depth, boolean getMax) {
        int winner = getWinnerMark(gameField);

        if (winner == PLAYER_1_MARKER) return depth - 10;
        if (winner == PLAYER_2_MARKER) return 10 - depth;
        if (isBoardFull(gameField)) return 0;

        if (getMax) {
            int highScore = -10000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameField.getCell(i, j) == EMPTY) {
                        GameField fieldCopy = copyField(gameField);
                        fieldCopy.setCell(i, j, PLAYER_2_MARKER);
                        int score = miniMaxAlgo(fieldCopy, depth + 1, false);
                        highScore = Math.max(score, highScore);
                    }
                }
            }
            return highScore;
        } else {
            int lowScore = 10000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameField.getCell(i, j) == EMPTY) {
                        GameField fieldCopy = copyField(gameField);
                        fieldCopy.setCell(i, j, PLAYER_1_MARKER);
                        int score = miniMaxAlgo(fieldCopy, depth + 1, true);
                        lowScore = Math.min(score, lowScore);
                    }
                }
            }
            return lowScore;
        }
    }

    private boolean isBoardFull(GameField gameField) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField.getCell(i, j) == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<Game> getGamesHistoryByUserId(UUID id) {
        return gameRepository.getGameHistoryByUserId(id);
    }

    @Override
    public List<LiderBoard> getLiderBoard(int limit) {
        if (limit < 1 || limit > 20) {
            throw new IncorrectDataProvided("Limit should be between 1 and 20");
        }
        return gameRepository.getLiderBoard(limit);
    }
}


