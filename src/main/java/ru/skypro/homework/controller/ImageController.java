package ru.skypro.homework.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    @GetMapping("/ads/image/{fileName:.+}")
    public ResponseEntity<Resource> getAdImage(@PathVariable String fileName) throws MalformedURLException {
        // ← Главная строчка — декодируем %20, кириллицу и т.д.
        String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);

        Path filePath = Paths.get("uploads/ads/" + decodedFileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(getMediaType(decodedFileName))
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users/image/{fileName:.+}")
    public ResponseEntity<Resource> getUserImage(@PathVariable String fileName) throws MalformedURLException {
        String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);

        Path filePath = Paths.get("uploads/users/" + decodedFileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(getMediaType(decodedFileName))
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }

    private MediaType getMediaType(String fileName) {
        String lower = fileName.toLowerCase();
        if (lower.endsWith(".png")) return MediaType.IMAGE_PNG;
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return MediaType.IMAGE_JPEG;
        if (lower.endsWith(".gif")) return MediaType.IMAGE_GIF;
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}
