package com.search.truyen.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.search.truyen.dtos.CommentDTO;
import com.search.truyen.service.CommentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.createComment(commentDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.updateComment(id, commentDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping("/story/{storyId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByStoryId(@PathVariable Long storyId) {
        return ResponseEntity.ok(commentService.getCommentsByStoryId(storyId));
    }

    @GetMapping("/chapter/{chapterId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByChapterId(@PathVariable Long chapterId) {
        return ResponseEntity.ok(commentService.getCommentsByChapterId(chapterId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.getCommentsByUserId(userId));
    }
}