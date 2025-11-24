package ru.skypro.homework.dto.response;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CommentsDto {
    private int count = 0;
    private List<CommentDto> results = new ArrayList<>();
}

