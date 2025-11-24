package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.response.CommentDto;
import ru.skypro.homework.model.AdComment;
import ru.skypro.homework.repository.AdCommentRepository;

import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AdCommentServiceImpl {

    private final AdCommentRepository adcommentRepository;

    public CommentDto toCommentDto(AdComment comment) {
        CommentDto dto = new CommentDto();
        dto.setPk(comment.getPk());
        dto.setAuthor(comment.getAuthor().getId());
        dto.setAuthorImage(comment.getAuthor().getImage());
        dto.setAuthorFirstName(comment.getAuthor().getFirstName());
        dto.setCreatedAt(comment.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        dto.setText(comment.getText());
        return dto;
    }
}