package ganisrum.tictactoe.domain.service;

import ganisrum.tictactoe.domain.model.Role;
import ganisrum.tictactoe.domain.model.User;
import ganisrum.tictactoe.exception.InvalidTokenException;
import ganisrum.tictactoe.security.JwtAuthentication;
import ganisrum.tictactoe.security.JwtProvider;
import ganisrum.tictactoe.security.UserRole;
import ganisrum.tictactoe.web.model.JwtRequest;
import ganisrum.tictactoe.web.model.JwtResponse;
import ganisrum.tictactoe.web.model.SignUpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;

    }


    @Override
    public boolean register(SignUpRequest request) {

        if (userService.existsByLogin(request.getLogin())) {
            return false;
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getLogin(), encodedPassword, Set.of(new UserRole(Role.USER)));
        userService.saveUser(user);
        return true;
    }

    @Override
    public JwtResponse signIn(JwtRequest request) {
        User user = userService.findByLogin(request.getLogin());
        return makeUpJwtResponse(user, null);
    }

    @Override
    public JwtResponse updateRefreshToken(String token) {
        if (jwtProvider.validateRefreshToken(token)) {
            User user = getUserFromToken(token);
            return makeUpJwtResponse(user, null);
        } else {
            throw new InvalidTokenException("Invalid token");
        }

    }

    @Override
    public JwtResponse updateAccessToken(String token) {
        if (jwtProvider.validateRefreshToken(token)) {
            User user = getUserFromToken(token);
            return makeUpJwtResponse(user, token);
        } else {
            throw new InvalidTokenException("Invalid token");
        }

    }

    private User getUserFromToken(String token) {
        return userService.findById(UUID.fromString(jwtProvider.getRefreshClaims(token).get("id", String.class)));
    }

    private JwtResponse makeUpJwtResponse(User user, String refreshToken) {
        String accessToken = jwtProvider.generateAccessToken(user);
        if (refreshToken == null) {
            refreshToken = jwtProvider.generateRefreshToken(user);
        }
        return new JwtResponse(accessToken, refreshToken);

    }

    @Override
    public JwtAuthentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthentication jwtAuthentication)
            return jwtAuthentication;

        return new JwtAuthentication(null, Set.of(), false);
    }

    @Override
    public User getCurrentUser() {
        JwtAuthentication authentication = getAuthentication();
        UUID userId = (UUID) authentication.getPrincipal();
        return userService.findById(userId);
    }


}