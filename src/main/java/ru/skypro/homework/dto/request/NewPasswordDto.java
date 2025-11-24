package ru.skypro.homework.dto.request;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class NewPasswordDto {
    private String currentPassword;
    private String newPassword;
}