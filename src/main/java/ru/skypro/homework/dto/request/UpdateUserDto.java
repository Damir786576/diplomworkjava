package ru.skypro.homework.dto.request;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UpdateUserDto {
    private String firstName;
    private String lastName;
    private String phone;
}
