package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.request.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.response.AdDto;
import ru.skypro.homework.dto.response.AdsDto;
import ru.skypro.homework.dto.response.ExtendedAdDto;
import ru.skypro.homework.service.AdService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    private final AdService adService;

    @GetMapping
    public ResponseEntity<AdsDto> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> createAd(
            @RequestParam("image") MultipartFile image,
            @RequestPart("properties") CreateOrUpdateAdDto dto,
            Authentication auth) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adService.createAd(dto, image, auth));
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDto> getMyAds(Authentication auth) {
        return ResponseEntity.ok(adService.getMyAds(auth));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAd(@PathVariable int id) {
        return ResponseEntity.ok(adService.getAd(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable int id, Authentication auth) {
        adService.deleteAd(id, auth);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAd(@PathVariable int id,
                                          @RequestBody CreateOrUpdateAdDto dto,
                                          Authentication auth) {
        return ResponseEntity.ok(adService.updateAd(id, dto, auth));
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateAdImage(@PathVariable int id,
                                              @RequestParam("image") MultipartFile file,
                                              Authentication auth) throws Exception {
        adService.updateAdImage(id, file, auth);
        return ResponseEntity.ok().build();
    }
}