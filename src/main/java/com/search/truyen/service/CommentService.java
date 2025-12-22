package com.search.truyen.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.search.truyen.dtos.CommentDTO;
import com.search.truyen.model.entities.Chapter;
import com.search.truyen.model.entities.Comment;
import com.search.truyen.model.entities.Story;
import com.search.truyen.model.entities.User;
import com.search.truyen.repository.ChapterRepository;
import com.search.truyen.repository.CommentRepository;
import com.search.truyen.repository.UserRepository;
import com.search.truyen.repository.storyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final storyRepository storyRepository;
    private final ChapterRepository chapterRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Comment createComment(CommentDTO commentDTO) {
        Story story = storyRepository.findById(commentDTO.getStoryId())
                .orElseThrow(() -> new RuntimeException("Story not found with id: " + commentDTO.getStoryId()));

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + commentDTO.getUserId()));

        Comment comment = Comment.builder()
                .story(story)
                .user(user)
                .content(commentDTO.getContent())
                .build();

        if (commentDTO.getChapterId() != null) {
            Chapter chapter = chapterRepository.findById(commentDTO.getChapterId())
                    .orElseThrow(() -> new RuntimeException("Chapter not found with id: " + commentDTO.getChapterId()));
            comment.setChapter(chapter);
        }

        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, CommentDTO commentDTO) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
        modelMapper.map(commentDTO, existingComment);
        return commentRepository.save(existingComment);
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByStoryId(Long storyId) {
        return commentRepository.findByStoryIdOrderByCreateAtDesc(storyId);
    }

    public List<Comment> getCommentsByChapterId(Long chapterId) {
        return commentRepository.findByChapterId(chapterId);
    }

    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }
}
