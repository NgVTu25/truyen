package com.search.truyen.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.search.truyen.dtos.CommentDTO;
import com.search.truyen.model.entities.*;
import com.search.truyen.repository.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final storyRepository storyRepository;
    private final ChapterRepository chapterRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CommentDTO createComment(CommentDTO commentDTO) {
        Story story = storyRepository.findById(commentDTO.getStoryId())
                .orElseThrow(() -> new RuntimeException("Story not found"));
        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setStory(story);
        comment.setUser(user);

        if (commentDTO.getChapterId() != null) {
            Chapter chapter = chapterRepository.findById(commentDTO.getChapterId())
                    .orElseThrow(() -> new RuntimeException("Chapter not found"));
            comment.setChapter(chapter);
        }

        return modelMapper.map(commentRepository.save(comment), CommentDTO.class);
    }

    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment existing = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        modelMapper.map(commentDTO, existing);
        existing.setId(id);
        return modelMapper.map(commentRepository.save(existing), CommentDTO.class);
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) throw new RuntimeException("Comment not found");
        commentRepository.deleteById(id);
    }

    public Optional<CommentDTO> getCommentById(Long id) {
        return commentRepository.findById(id)
                .map(c -> modelMapper.map(c, CommentDTO.class));
    }

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(c -> modelMapper.map(c, CommentDTO.class))
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getCommentsByStoryId(Long storyId) {
        return commentRepository.findByStoryIdOrderByCreateAtDesc(storyId).stream()
                .map(c -> modelMapper.map(c, CommentDTO.class))
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getCommentsByChapterId(Long chapterId) {
        return commentRepository.findByChapterId(chapterId).stream()
                .map(c -> modelMapper.map(c, CommentDTO.class))
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId).stream()
                .map(c -> modelMapper.map(c, CommentDTO.class))
                .collect(Collectors.toList());
    }
}