package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.response.CommentDto;
import ru.skypro.homework.model.AdComment;

@Component
public class AdCommentMapper {

    public CommentDto toCommentDto(AdComment comment) {
        CommentDto dto = new CommentDto();
        dto.setPk(comment.getPk());
        dto.setAuthor(comment.getAuthor().getId());
        dto.setAuthorFirstName(comment.getAuthor().getFirstName());
        dto.setAuthorImage(comment.getAuthor().getImage() != null
                ? "/users/image/" + comment.getAuthor().getImage() : null);
        dto.setCreatedAt(comment.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli());
        dto.setText(comment.getText());
        return dto;
    }
}
