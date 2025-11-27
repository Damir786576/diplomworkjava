// UserService.java
package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.request.NewPasswordDto;
import ru.skypro.homework.dto.request.UpdateUserDto;
import ru.skypro.homework.dto.response.UserDto;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto getMe(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        return userMapper.toUserDto(user);
    }

    public UserDto updateMe(UpdateUserDto dto, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        userMapper.updateUserFromDto(user, dto);
        user = userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    public void setPassword(NewPasswordDto dto, Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new SecurityException("Старый пароль неверный");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    public void updateAvatar(MultipartFile file, Authentication auth) throws Exception {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        String fileName = saveImage(file, "users");
        user.setImage(fileName);
        userRepository.save(user);
    }

    private String saveImage(MultipartFile file, String folder) throws Exception {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/" + folder + "/" + fileName);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path);
        return fileName;
    }
}
