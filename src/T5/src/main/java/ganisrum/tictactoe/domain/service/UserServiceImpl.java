package ganisrum.tictactoe.domain.service;


import ganisrum.tictactoe.domain.model.User;
import ganisrum.tictactoe.domain.repositoty.DomainUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final DomainUserRepository domainUserRepository;

    public UserServiceImpl(DomainUserRepository domainUserRepository) {
        this.domainUserRepository = domainUserRepository;
    }


    @Override
    public User saveUser(User user) {
        domainUserRepository.createUser(user);
        return user;
    }


    @Override
    public User findByLogin(String login) {
        return domainUserRepository.findByLogin(login);
    }


    @Override
    public User findById(UUID id) {
        return domainUserRepository.findById(id);
    }

    @Override
    public boolean existsByLogin(String login) {
        return domainUserRepository.existsByLogin(login);
    }
}
