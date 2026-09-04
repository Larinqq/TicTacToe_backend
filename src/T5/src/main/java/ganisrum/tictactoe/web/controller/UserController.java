package ganisrum.tictactoe.web.controller;

import ganisrum.tictactoe.domain.service.UserService;
import ganisrum.tictactoe.web.mapper.UserDtoMapper;
import ganisrum.tictactoe.web.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable UUID id) {
        return ResponseEntity.ok(UserDtoMapper.userToDto(userService.findById(id)));
    }

}
