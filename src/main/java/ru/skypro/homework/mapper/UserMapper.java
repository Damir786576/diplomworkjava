package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.response.UserDto;
import ru.skypro.homework.dto.request.UpdateUserDto;
import ru.skypro.homework.model.User;

@Component
public class UserMapper {

    public UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole().name());
        dto.setImage(user.getImage() != null ? "/users/image/" + user.getId() : null);
        return dto;
    }

    public void updateUserFromDto(User user, UpdateUserDto dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
    }
}
