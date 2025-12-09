package com.search.truyen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.truyen.dtos.HistoryDTO;
import com.search.truyen.model.entities.Chapter;
import com.search.truyen.model.entities.History;
import com.search.truyen.model.entities.Story;
import com.search.truyen.model.entities.User;
import com.search.truyen.service.HistoryService;
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

@WebMvcTest(HistoryController.class)
class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HistoryService historyService;

    private History testHistory;
    private HistoryDTO testHistoryDTO;

    @BeforeEach
    void setUp() {
        User user = User.builder().id(1L).username("testuser").build();
        Story story = Story.builder().id(1L).title("Test Story").build();
        Chapter chapter = Chapter.builder().id(1L).title("Chapter 1").build();

        testHistory = History.builder()
                .id(1L)
                .user(user)
                .story(story)
                .chapter(chapter)
                .lastPage(10)
                .build();

        testHistoryDTO = new HistoryDTO();
        testHistoryDTO.setId(1L);
        testHistoryDTO.setUserId(1L);
        testHistoryDTO.setStoryId(1L);
        testHistoryDTO.setChapterId(1L);
        testHistoryDTO.setLastPage(10);
    }

    @Test
    void saveHistory_ShouldReturnSavedHistory_WhenValidInput() throws Exception {
        when(historyService.createOrUpdateHistory(any(HistoryDTO.class))).thenReturn(testHistory);

        mockMvc.perform(post("/api/history/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testHistoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.storyId").value(1))
                .andExpect(jsonPath("$.lastPage").value(10));
    }

    @Test
    void deleteHistory_ShouldReturnNoContent_WhenHistoryExists() throws Exception {
        doNothing().when(historyService).deleteHistory(1L);

        mockMvc.perform(delete("/api/history/delete/1"))
                .andExpect(status().isNoContent());

        verify(historyService, times(1)).deleteHistory(1L);
    }

    @Test
    void getHistoryById_ShouldReturnHistory_WhenHistoryExists() throws Exception {
        when(historyService.getHistoryById(1L)).thenReturn(Optional.of(testHistory));

        mockMvc.perform(get("/api/history/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lastPage").value(10));
    }

    @Test
    void getHistoriesByUserId_ShouldReturnListOfHistories() throws Exception {
        List<History> histories = Arrays.asList(testHistory);
        when(historyService.getHistoriesByUserId(1L)).thenReturn(histories);

        mockMvc.perform(get("/api/history/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userId").value(1));
    }

    @Test
    void getHistoriesByStoryId_ShouldReturnListOfHistories() throws Exception {
        List<History> histories = Arrays.asList(testHistory);
        when(historyService.getHistoriesByStoryId(1L)).thenReturn(histories);

        mockMvc.perform(get("/api/history/story/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getHistoryByUserIdAndStoryId_ShouldReturnHistory_WhenExists() throws Exception {
        when(historyService.getHistoryByUserIdAndStoryId(1L, 1L)).thenReturn(Optional.of(testHistory));

        mockMvc.perform(get("/api/history/user/1/story/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.storyId").value(1));
    }

    @Test
    void getAllHistories_ShouldReturnAllHistories() throws Exception {
        List<History> histories = Arrays.asList(testHistory);
        when(historyService.getAllHistories()).thenReturn(histories);

        mockMvc.perform(get("/api/history/get_all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
