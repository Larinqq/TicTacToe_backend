package ganisrum.tictactoe.web.controller;

import ganisrum.tictactoe.domain.model.Game;
import ganisrum.tictactoe.domain.model.GameField;
import ganisrum.tictactoe.domain.service.GameService;
import ganisrum.tictactoe.domain.service.UserService;
import ganisrum.tictactoe.web.mapper.GameDtoMapper;
import ganisrum.tictactoe.web.model.GameDTO;
import ganisrum.tictactoe.web.model.GameFieldDTO;
import ganisrum.tictactoe.web.model.GameHistoryDto;
import ganisrum.tictactoe.web.model.GameRequestDTO;
import ganisrum.tictactoe.web.model.LiderBoardDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final UserService userService;

    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @PostMapping("/new")
    public ResponseEntity<GameDTO> createNewGame(@Valid @RequestBody GameRequestDTO gameRequest, @AuthenticationPrincipal UUID userId) {
        Game game = gameService.createNewGame(userId, gameRequest.isVsComputer(), gameRequest.isHostMarkX());
        return ResponseEntity.ok(GameDtoMapper.gameToDto(game));
    }


    @PostMapping("/join/{gameId}")
    public ResponseEntity<GameDTO> joinGame(@PathVariable UUID gameId, @AuthenticationPrincipal UUID userId) {
        Game game = gameService.joinGame(gameId, userId);
        return ResponseEntity.ok(GameDtoMapper.gameToDto(game));
    }

    @GetMapping("/find")
    public List<UUID> findAvailable(@AuthenticationPrincipal UUID userId) {
        return gameService.findAvailableGamesid(userId);
    }


    @PostMapping("/{id}")
    public ResponseEntity<GameDTO> makePlayerMove(@PathVariable UUID id, @Valid @RequestBody GameFieldDTO givenFieldDto, @AuthenticationPrincipal UUID userId) {
        GameField givenField = GameDtoMapper.fieldToDomain(givenFieldDto);
        Game updatedGame = gameService.makePlayerMove(id, givenField, userId);
        return ResponseEntity.ok(GameDtoMapper.gameToDto(updatedGame));

    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable UUID id) {
        Game game = gameService.getGameById(id);
        return ResponseEntity.ok(GameDtoMapper.gameToDto(game));
    }

    @GetMapping("/all")
    public List<UUID> getAllGames() {
        return gameService.getAllGamesIds();
    }

    @GetMapping("/history")
    public ResponseEntity<List<GameHistoryDto>> getGamesHistoryByUserId(@AuthenticationPrincipal UUID userId) {
        List<GameHistoryDto> history = gameService.getGamesHistoryByUserId(userId).stream()
                .map(game -> GameDtoMapper.gameHistoryToDto(game))
                .toList();
        if (history.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(history);
    }

    @GetMapping("/lider-board")
    public ResponseEntity<List<LiderBoardDTO>> getLiderBoardDto(@RequestParam(defaultValue = "5") int limit) {
        List<LiderBoardDTO> liderBoard = gameService.getLiderBoard(limit).stream()
                .map(liders -> GameDtoMapper.liderBoardToDto(liders, userService.findById(liders.getUserid()).getLogin()))
                .toList();
        if (liderBoard.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(liderBoard);
    }
}

