package com.search.truyen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.truyen.dtos.CommentDTO;
import com.search.truyen.model.entities.Comment;
import com.search.truyen.model.entities.Story;
import com.search.truyen.model.entities.User;
import com.search.truyen.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    private Comment testComment;
    private CommentDTO testCommentDTO;

    @BeforeEach
    void setUp() {
        Story story = Story.builder().id(1L).title("Test Story").build();
        User user = User.builder().id(1L).username("testuser").build();

        testComment = Comment.builder()
                .id(1L)
                .story(story)
                .user(user)
                .content("Test comment content")
                .build();

        testCommentDTO = new CommentDTO();
        testCommentDTO.setId(1L);
        testCommentDTO.setStoryId(1L);
        testCommentDTO.setUserId(1L);
        testCommentDTO.setContent("Test comment content");
    }

    @Test
    void createComment_ShouldReturnCreatedComment_WhenValidInput() throws Exception {
        when(commentService.createComment(any(CommentDTO.class))).thenReturn(testComment);

        mockMvc.perform(post("/api/comments/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCommentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test comment content"))
                .andExpect(jsonPath("$.storyId").value(1))
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    void updateComment_ShouldReturnUpdatedComment_WhenCommentExists() throws Exception {
        when(commentService.updateComment(anyLong(), any(CommentDTO.class))).thenReturn(testComment);

        mockMvc.perform(put("/api/comments/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCommentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test comment content"));
    }

    @Test
    void deleteComment_ShouldReturnNoContent_WhenCommentExists() throws Exception {
        doNothing().when(commentService).deleteComment(1L);

        mockMvc.perform(delete("/api/comments/delete/1"))
                .andExpect(status().isNoContent());

        verify(commentService, times(1)).deleteComment(1L);
    }

    @Test
    void getCommentById_ShouldReturnComment_WhenCommentExists() throws Exception {
        when(commentService.getCommentById(1L)).thenReturn(Optional.of(testComment));

        mockMvc.perform(get("/api/comments/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("Test comment content"));
    }

    @Test
    void getCommentsByStoryId_ShouldReturnListOfComments() throws Exception {
        List<Comment> comments = Arrays.asList(testComment);
        when(commentService.getCommentsByStoryId(1L)).thenReturn(comments);

        mockMvc.perform(get("/api/comments/story/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].content").value("Test comment content"));
    }

    @Test
    void getCommentsByUserId_ShouldReturnListOfComments() throws Exception {
        List<Comment> comments = Arrays.asList(testComment);
        when(commentService.getCommentsByUserId(1L)).thenReturn(comments);

        mockMvc.perform(get("/api/comments/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getAllComments_ShouldReturnAllComments() throws Exception {
        List<Comment> comments = Arrays.asList(testComment);
        when(commentService.getAllComments()).thenReturn(comments);

        mockMvc.perform(get("/api/comments/get_all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
