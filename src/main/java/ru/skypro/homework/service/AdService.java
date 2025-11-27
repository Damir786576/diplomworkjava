package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.request.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.response.AdDto;
import ru.skypro.homework.dto.response.AdsDto;
import ru.skypro.homework.dto.response.ExtendedAdDto;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;

    public AdsDto getAllAds() {
        List<AdDto> ads = adRepository.findAll().stream()
                .map(adMapper::toAdDto)
                .collect(Collectors.toList());

        AdsDto dto = new AdsDto();
        dto.setCount(ads.size());
        dto.setResults(ads);
        return dto;
    }

    public AdsDto getMyAds(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();
        List<AdDto> ads = adRepository.findAllByAuthorId(user.getId()).stream()
                .map(adMapper::toAdDto)
                .collect(Collectors.toList());

        AdsDto dto = new AdsDto();
        dto.setCount(ads.size());
        dto.setResults(ads);
        return dto;
    }

    public AdDto createAd(CreateOrUpdateAdDto dto, MultipartFile image, Authentication auth) throws Exception {
        User author = userRepository.findByUsername(auth.getName()).orElseThrow();

        Ad ad = new Ad();
        ad.setTitle(dto.getTitle());
        ad.setPrice(dto.getPrice());
        ad.setDescription(dto.getDescription());
        ad.setAuthor(author);

        if (image != null && !image.isEmpty()) {
            String fileName = saveImage(image, "ads");
            ad.setImage(fileName);
        }

        ad = adRepository.save(ad);
        return adMapper.toAdDto(ad);
    }

    public ExtendedAdDto getAd(int id) {
        Ad ad = adRepository.findById(id).orElseThrow();
        return adMapper.toExtendedAdDto(ad);
    }

    public void deleteAd(int id, Authentication auth) {
        Ad ad = adRepository.findById(id).orElseThrow();
        if (!ad.getAuthor().getUsername().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Это не твоё объявление");
        }
        adRepository.delete(ad);
    }

    public AdDto updateAd(int id, CreateOrUpdateAdDto dto, Authentication auth) {
        Ad ad = adRepository.findById(id).orElseThrow();
        if (!ad.getAuthor().getUsername().equals(auth.getName())) {
            throw new SecurityException("Нет прав");
        }
        adMapper.updateAdFromDto(ad, dto);
        ad = adRepository.save(ad);
        return adMapper.toAdDto(ad);
    }

    public void updateAdImage(int id, MultipartFile image, Authentication auth) throws Exception {
        Ad ad = adRepository.findById(id).orElseThrow();
        if (!ad.getAuthor().getUsername().equals(auth.getName())) {
            throw new SecurityException("Нет прав");
        }
        String fileName = saveImage(image, "ads");
        ad.setImage(fileName);
        adRepository.save(ad);
    }

    private String saveImage(MultipartFile file, String folder) throws Exception {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get("uploads/" + folder + "/" + fileName);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path);
        return fileName;
    }
}