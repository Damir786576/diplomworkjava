package ru.skypro.homework;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FoldersInit implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Files.createDirectories(Paths.get("uploads/ads"));
        Files.createDirectories(Paths.get("uploads/users"));
    }
}
