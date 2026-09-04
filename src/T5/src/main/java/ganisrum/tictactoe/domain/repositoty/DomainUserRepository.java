package ganisrum.tictactoe.domain.repositoty;

import ganisrum.tictactoe.domain.model.User;

import java.util.UUID;

public interface DomainUserRepository {

    void createUser(User user);

    User findByLogin(String login);

    User findById(UUID id);

    boolean existsByLogin(String login);

}

