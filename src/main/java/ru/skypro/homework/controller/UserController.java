package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.request.NewPasswordDto;
import ru.skypro.homework.dto.request.UpdateUserDto;
import ru.skypro.homework.dto.response.UserDto;
import ru.skypro.homework.service.UserService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDto dto,
                                            Authentication auth) {
        userService.setPassword(dto, auth);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe(Authentication auth) {
        return ResponseEntity.ok(userService.getMe(auth));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateMe(@RequestBody UpdateUserDto dto,
                                            Authentication auth) {
        return ResponseEntity.ok(userService.updateMe(dto, auth));
    }

    @PatchMapping(value = "/me/image", consumes = "multipart/form-data")
    public ResponseEntity<Void> updateAvatar(@RequestParam("image") MultipartFile file,
                                             Authentication auth) throws Exception {
        userService.updateAvatar(file, auth);
        return ResponseEntity.ok().build();
    }
}
