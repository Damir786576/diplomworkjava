package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.response.AdDto;
import ru.skypro.homework.dto.response.ExtendedAdDto;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AdServiceImpl {

    private final AdRepository adRepository;
    private final UserRepository userRepository;

    public AdDto toAdDto(Ad ad) {
        AdDto dto = new AdDto();
        dto.setPk(ad.getPk());
        dto.setAuthor(ad.getAuthor().getId());
        dto.setImage(ad.getImage() != null ? "/ads/image/" + ad.getPk() : null);
        dto.setPrice(ad.getPrice());
        dto.setTitle(ad.getTitle());
        return dto;
    }

    public ExtendedAdDto toExtendedAdDto(Ad ad) {
        ExtendedAdDto dto = new ExtendedAdDto();
        dto.setPk(ad.getPk());
        dto.setAuthorFirstName(ad.getAuthor().getFirstName());
        dto.setAuthorLastName(ad.getAuthor().getLastName());
        dto.setDescription(ad.getDescription());
        dto.setEmail(ad.getAuthor().getUsername());
        dto.setImage(ad.getImage() != null ? "/ads/image/" + ad.getPk() : null);
        dto.setPhone(ad.getAuthor().getPhone());
        dto.setPrice(ad.getPrice());
        dto.setTitle(ad.getTitle());
        return dto;
    }
}