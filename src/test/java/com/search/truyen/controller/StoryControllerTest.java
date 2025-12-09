package com.search.truyen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.truyen.dtos.storyDTO;
import com.search.truyen.enums.Storytype;
import com.search.truyen.model.entities.Story;
import com.search.truyen.service.StoryService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StoryController.class)
class StoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StoryService storyService;

    private Story testStory;
    private storyDTO testStoryDTO;

    @BeforeEach
    void setUp() {
        testStory = Story.builder()
                .id(1L)
                .title("Test Story")
                .description("Test Description")
                .coverImage("test.jpg")
                .type(Storytype.COMIC)
                .build();

        testStoryDTO = new storyDTO();
        testStoryDTO.setId(1L);
        testStoryDTO.setTitle("Test Story");
        testStoryDTO.setDescription("Test Description");
        testStoryDTO.setCoverImage("test.jpg");
        testStoryDTO.setType(Storytype.COMIC);
    }

    @Test
    void createStory_ShouldReturnCreatedStory_WhenValidInput() throws Exception {
        when(storyService.createStory(any(storyDTO.class))).thenReturn(testStory);

        mockMvc.perform(post("/api/stories/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testStoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Story"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void updateStory_ShouldReturnUpdatedStory_WhenStoryExists() throws Exception {
        when(storyService.getStoryById(1L)).thenReturn(Optional.of(testStory));
        when(storyService.updateStory(any(Story.class))).thenReturn(testStory);

        testStoryDTO.setTitle("Updated Story");

        mockMvc.perform(put("/api/stories/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testStoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Story"));
    }

    @Test
    void deleteStory_ShouldReturnNoContent_WhenStoryExists() throws Exception {
        doNothing().when(storyService).deleteStory(1L);

        mockMvc.perform(delete("/api/stories/delete/1"))
                .andExpect(status().isNoContent());

        verify(storyService, times(1)).deleteStory(1L);
    }

    @Test
    void getStoryById_ShouldReturnStory_WhenStoryExists() throws Exception {
        when(storyService.getStoryById(1L)).thenReturn(Optional.of(testStory));

        mockMvc.perform(get("/api/stories/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Story"));
    }

    @Test
    void getStoryByTitle_ShouldReturnStory_WhenStoryExists() throws Exception {
        when(storyService.getStoryByTitle("Test Story")).thenReturn(Optional.of(testStory));

        mockMvc.perform(get("/api/stories/search")
                .param("title", "Test Story"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Story"));
    }

    @Test
    void getAllStories_ShouldReturnListOfStories() throws Exception {
        Story story2 = Story.builder()
                .id(2L)
                .title("Story 2")
                .description("Description 2")
                .type(Storytype.NOVEL)
                .build();

        List<Story> stories = Arrays.asList(testStory, story2);
        when(storyService.getAllStories()).thenReturn(stories);

        mockMvc.perform(get("/api/stories/get_all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Test Story"))
                .andExpect(jsonPath("$[1].title").value("Story 2"));
    }
}
