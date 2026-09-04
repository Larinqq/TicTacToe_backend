package ganisrum.tictactoe.datasource.mapper;


import ganisrum.tictactoe.datasource.model.UserEntity;
import ganisrum.tictactoe.domain.model.Role;
import ganisrum.tictactoe.domain.model.User;
import ganisrum.tictactoe.security.UserRole;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserMapper() {

    }

    public static UserEntity userToEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setLogin(user.getLogin());
        entity.setPassword(user.getPassword());
        Set<Role> roleEnums = user.getRoles().stream()
                .map(UserRole::role)
                .collect(Collectors.toSet());
        entity.setRoles(roleEnums);
        return entity;
    }

    public static User userToDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setLogin(entity.getLogin());
        user.setPassword(entity.getPassword());
        entity.getRoles().forEach(user::addRole);
        return user;
    }

}


