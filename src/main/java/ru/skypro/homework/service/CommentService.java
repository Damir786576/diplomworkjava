package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.request.CreateOrUpdateCommentDto;
import ru.skypro.homework.dto.response.CommentDto;
import ru.skypro.homework.dto.response.CommentsDto;
import ru.skypro.homework.mapper.AdCommentMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.AdComment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdCommentRepository;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final AdCommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdCommentMapper commentMapper;

    public CommentsDto getComments(int adId) {
        List<CommentDto> comments = commentRepository.findAllByAdPk(adId).stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());

        CommentsDto dto = new CommentsDto();
        dto.setCount(comments.size());
        dto.setResults(comments);
        return dto;
    }

    public CommentDto addComment(int adId, CreateOrUpdateCommentDto dto, Authentication auth) {
        User author = userRepository.findByUsername(auth.getName()).orElseThrow();
        Ad ad = adRepository.findById(adId).orElseThrow();

        AdComment comment = new AdComment();
        comment.setText(dto.getText());
        comment.setAuthor(author);
        comment.setAd(ad);
        comment.setCreatedAt(LocalDateTime.now());

        comment = commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    public CommentDto updateComment(int adId, int commentId, CreateOrUpdateCommentDto dto, Authentication auth) {
        AdComment comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getAuthor().getUsername().equals(auth.getName())) {
            throw new SecurityException("Нет прав");
        }
        comment.setText(dto.getText());
        comment = commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    public void deleteComment(int adId, int commentId, Authentication auth) {
        AdComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Комментарий не найден"));
        if (!comment.getAuthor().getUsername().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Нельзя удалять чужой комментарий");
        }
        commentRepository.delete(comment);
    }
}
