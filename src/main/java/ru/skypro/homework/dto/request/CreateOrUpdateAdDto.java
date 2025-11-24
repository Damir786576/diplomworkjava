package ru.skypro.homework.dto.request;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CreateOrUpdateAdDto {
    private String title;
    private Integer price;
    private String description;
}
