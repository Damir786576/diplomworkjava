package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.request.CreateOrUpdateCommentDto;
import ru.skypro.homework.dto.response.CommentDto;
import ru.skypro.homework.dto.response.CommentsDto;
import ru.skypro.homework.service.CommentService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AdCommentController {

    private final CommentService commentService;

    @GetMapping("/ads/{adId}/comments")
    public ResponseEntity<CommentsDto> getComments(@PathVariable int adId) {
        return ResponseEntity.ok(commentService.getComments(adId));
    }

    @PostMapping("/ads/{adId}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable int adId,
                                                 @RequestBody CreateOrUpdateCommentDto dto,
                                                 Authentication auth) {
        return ResponseEntity.ok(commentService.addComment(adId, dto, auth));
    }

    @PatchMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable int adId,
                                                    @PathVariable int commentId,
                                                    @RequestBody CreateOrUpdateCommentDto dto,
                                                    Authentication auth) {
        return ResponseEntity.ok(commentService.updateComment(adId, commentId, dto, auth));
    }

    @DeleteMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int adId,
                                              @PathVariable int commentId,
                                              Authentication auth) {
        commentService.deleteComment(adId, commentId, auth);
        return ResponseEntity.noContent().build();
    }
}
