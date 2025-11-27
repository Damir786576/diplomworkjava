package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.request.RegisterDto;
import ru.skypro.homework.dto.request.Role;
import ru.skypro.homework.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public boolean login(String username, String password) {
        try {
            UserDetails user = userDetailsManager.loadUserByUsername(username);
            return passwordEncoder.matches(password, user.getPassword());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean register(RegisterDto dto) {
        String username = dto.getUsername();
        if (userDetailsManager.userExists(username)) {
            return false;
        }
        ru.skypro.homework.model.User appUser = new ru.skypro.homework.model.User();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        appUser.setFirstName(dto.getFirstName());
        appUser.setLastName(dto.getLastName());
        appUser.setPhone(dto.getPhone());
        Role role = dto.getRole() != null ? dto.getRole() : Role.USER;
        appUser.setRole(role);
        userRepository.save(appUser);

        userDetailsManager.createUser(
                User.withUsername(username)
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .roles(role.name())
                        .build());
        return true;
    }
}