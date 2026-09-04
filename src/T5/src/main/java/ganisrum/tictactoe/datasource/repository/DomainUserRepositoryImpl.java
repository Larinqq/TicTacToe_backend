package ganisrum.tictactoe.datasource.repository;

import ganisrum.tictactoe.datasource.model.UserEntity;
import ganisrum.tictactoe.domain.model.User;
import ganisrum.tictactoe.domain.repositoty.DomainUserRepository;
import ganisrum.tictactoe.exception.UserAlreadyExistException;
import ganisrum.tictactoe.exception.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static ganisrum.tictactoe.datasource.mapper.UserMapper.userToDomain;
import static ganisrum.tictactoe.datasource.mapper.UserMapper.userToEntity;

@Component
public class DomainUserRepositoryImpl implements DomainUserRepository {
    private final UserRepository userRepository;


    public DomainUserRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void createUser(User user) {
        UserEntity entity = userToEntity(user);
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new UserAlreadyExistException("User with login: " + user.getLogin() + " ");
        }
        userRepository.save(userToEntity(user));

    }

    @Override
    public User findByLogin(String login) {
        UserEntity entity = userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("User with login: " + login + " not found!"));
        return userToDomain(entity);
    }

    @Override
    public User findById(UUID id) {
        UserEntity entity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userToDomain(entity);
    }

    @Override
    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);

    }
}
