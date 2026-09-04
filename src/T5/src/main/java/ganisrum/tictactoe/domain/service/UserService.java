package ganisrum.tictactoe.domain.service;

import ganisrum.tictactoe.domain.model.User;

import java.util.UUID;

public interface UserService {

    User saveUser(User user);

    User findByLogin(String login);

    User findById(UUID id);

    boolean existsByLogin(String login);

}
