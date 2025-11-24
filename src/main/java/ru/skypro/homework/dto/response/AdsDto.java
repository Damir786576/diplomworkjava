package ru.skypro.homework.dto.response;

import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AdsDto {
    private int count = 0;
    private List<AdDto> results = new ArrayList<>();
}
