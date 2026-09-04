package ganisrum.tictactoe.web.controller;

import ganisrum.tictactoe.domain.service.AuthService;
import ganisrum.tictactoe.exception.UserAlreadyExistException;
import ganisrum.tictactoe.security.JwtProvider;
import ganisrum.tictactoe.web.mapper.UserDtoMapper;
import ganisrum.tictactoe.web.model.JwtRequest;
import ganisrum.tictactoe.web.model.JwtResponse;
import ganisrum.tictactoe.web.model.RefreshTokenRequest;
import ganisrum.tictactoe.web.model.SignUpRequest;
import ganisrum.tictactoe.web.model.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;

    public AuthController(AuthService authService, JwtProvider jwtProvider) {
        this.authService = authService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest request) {
        boolean success = authService.register(request);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Success, user has been created");
        } else {
            throw new UserAlreadyExistException("User already exist");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest jwtRequest) {
        return ResponseEntity.ok(authService.signIn(jwtRequest));
    }

    @PostMapping("/update-access")
    public ResponseEntity<JwtResponse> updateAccess(@Valid @RequestBody RefreshTokenRequest token) {
        return ResponseEntity.ok(authService.updateAccessToken(token.refreshToken()));
    }

    @PostMapping("/update-refresh")
    public ResponseEntity<JwtResponse> updateRefresh(@Valid @RequestBody RefreshTokenRequest token) {
        return ResponseEntity.ok(authService.updateRefreshToken(token.refreshToken()));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> whoAmI() {
        return ResponseEntity.ok(UserDtoMapper.userToDto(authService.getCurrentUser()));
    }

}

