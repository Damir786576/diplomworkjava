package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.request.CreateOrUpdateCommentDto;
import ru.skypro.homework.dto.response.CommentDto;
import ru.skypro.homework.dto.response.CommentsDto;

import java.util.ArrayList;

@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AdCommentController {

    @GetMapping("/ads/{adId}/comments")
    public ResponseEntity<CommentsDto> getComments(@PathVariable int adId) {
        CommentsDto res = new CommentsDto();
        res.setCount(0);
        res.setResults(new ArrayList<>());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/ads/{adId}/comments")
    public ResponseEntity<CommentDto> addComment(
            @PathVariable int adId,
            @RequestBody CreateOrUpdateCommentDto dto) {
        return ResponseEntity.ok(new CommentDto());
    }

    @PatchMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable int adId,
            @PathVariable int commentId,
            @RequestBody CreateOrUpdateCommentDto dto) {
        return ResponseEntity.ok(new CommentDto());
    }

    @DeleteMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable int adId,
            @PathVariable int commentId) {
        return ResponseEntity.noContent().build();
    }
}