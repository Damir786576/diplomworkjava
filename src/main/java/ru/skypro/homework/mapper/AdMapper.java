package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.response.AdDto;
import ru.skypro.homework.dto.response.ExtendedAdDto;
import ru.skypro.homework.dto.request.CreateOrUpdateAdDto;
import ru.skypro.homework.model.Ad;

@Component
public class AdMapper {

    public AdDto toAdDto(Ad ad) {
        AdDto dto = new AdDto();
        dto.setPk(ad.getPk());
        dto.setAuthor(ad.getAuthor().getId());
        dto.setPrice(ad.getPrice());
        dto.setTitle(ad.getTitle());
        dto.setImage(ad.getImage() != null ? "/ads/image/" + ad.getPk() : null);
        return dto;
    }

    public ExtendedAdDto toExtendedAdDto(Ad ad) {
        ExtendedAdDto dto = new ExtendedAdDto();
        dto.setPk(ad.getPk());
        dto.setAuthorFirstName(ad.getAuthor().getFirstName());
        dto.setAuthorLastName(ad.getAuthor().getLastName());
        dto.setDescription(ad.getDescription());
        dto.setEmail(ad.getAuthor().getUsername());
        dto.setPhone(ad.getAuthor().getPhone());
        dto.setPrice(ad.getPrice());
        dto.setTitle(ad.getTitle());
        dto.setImage(ad.getImage() != null ? "/ads/image/" + ad.getPk() : null);
        return dto;
    }

    public void updateAdFromDto(Ad ad, CreateOrUpdateAdDto dto) {
        ad.setTitle(dto.getTitle());
        ad.setPrice(dto.getPrice());
        ad.setDescription(dto.getDescription());
    }
}