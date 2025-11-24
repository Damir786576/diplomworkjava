package ru.skypro.homework.dto.request;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CreateOrUpdateCommentDto {
    private String text;
}
