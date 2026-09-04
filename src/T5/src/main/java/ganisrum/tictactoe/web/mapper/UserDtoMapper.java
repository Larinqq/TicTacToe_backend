package ganisrum.tictactoe.web.mapper;

import ganisrum.tictactoe.domain.model.User;
import ganisrum.tictactoe.web.model.UserDTO;

public class UserDtoMapper {

    public static UserDTO userToDto(User user) {
        if (user == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        return userDTO;
    }

    public static User userToDomain(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setLogin(userDTO.getLogin());
        return user;
    }
}