package com.search.truyen.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.search.truyen.dtos.CommentDTO;
import com.search.truyen.model.entities.Comment;
import com.search.truyen.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        Comment createdComment = commentService.createComment(commentDTO);
        return ResponseEntity.ok(mapToDTO(createdComment));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        Comment updatedComment = commentService.updateComment(id, commentDTO);
        return ResponseEntity.ok(mapToDTO(updatedComment));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
        return ResponseEntity.ok(mapToDTO(comment));
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        List<CommentDTO> commentDTOs = comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }

    @GetMapping("/story/{storyId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByStoryId(@PathVariable Long storyId) {
        List<Comment> comments = commentService.getCommentsByStoryId(storyId);
        List<CommentDTO> commentDTOs = comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }

    @GetMapping("/chapter/{chapterId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByChapterId(@PathVariable Long chapterId) {
        List<Comment> comments = commentService.getCommentsByChapterId(chapterId);
        List<CommentDTO> commentDTOs = comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByUserId(@PathVariable Long userId) {
        List<Comment> comments = commentService.getCommentsByUserId(userId);
        List<CommentDTO> commentDTOs = comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }
}
