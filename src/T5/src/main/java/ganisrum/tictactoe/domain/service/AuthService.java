package ganisrum.tictactoe.domain.service;

import ganisrum.tictactoe.domain.model.User;
import ganisrum.tictactoe.security.JwtAuthentication;
import ganisrum.tictactoe.web.model.JwtRequest;
import ganisrum.tictactoe.web.model.JwtResponse;
import ganisrum.tictactoe.web.model.SignUpRequest;

public interface AuthService {

    boolean register(SignUpRequest request);

    JwtResponse signIn(JwtRequest request);

    JwtResponse updateRefreshToken(String token);

    JwtResponse updateAccessToken(String token);

    JwtAuthentication getAuthentication();

    User getCurrentUser();
}
