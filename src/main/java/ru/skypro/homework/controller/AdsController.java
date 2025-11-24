package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.request.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.response.AdDto;
import ru.skypro.homework.dto.response.AdsDto;
import ru.skypro.homework.dto.response.ExtendedAdDto;

import java.util.ArrayList;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdsController {

    @GetMapping
    public ResponseEntity<AdsDto> getAllAds() {
        AdsDto ads = new AdsDto();
        ads.setCount(0);
        ads.setResults(new ArrayList<>());
        return ResponseEntity.ok(ads);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> createAd(
            @RequestParam("image") MultipartFile image,
            @RequestPart("properties") CreateOrUpdateAdDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new AdDto());
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDto> getMyAds() {
        AdsDto ads = new AdsDto();
        ads.setCount(0);
        ads.setResults(new ArrayList<>());
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDto> getAd(@PathVariable int id) {
        return ResponseEntity.ok(new ExtendedAdDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable int id) {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAd(@PathVariable int id, @RequestBody CreateOrUpdateAdDto dto) {
        return ResponseEntity.ok(new AdDto());
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateAdImage(@PathVariable int id, @RequestParam("image") MultipartFile file) {
        return ResponseEntity.ok().build();
    }
}