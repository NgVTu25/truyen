package com.search.truyen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.truyen.dtos.ChapterDTO;
import com.search.truyen.model.entities.Chapter;
import com.search.truyen.model.entities.Story;
import com.search.truyen.service.ChapterService;
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

@WebMvcTest(ChapterController.class)
class ChapterControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private ChapterService chapterService;

        private Chapter testChapter;
        private ChapterDTO testChapterDTO;
        private Story testStory;

        @BeforeEach
        void setUp() {
                testStory = Story.builder()
                                .id(1L)
                                .title("Test Story")
                                .build();

                testChapter = Chapter.builder()
                                .id(1L)
                                .title("Chapter 1")
                                .chapterNumber(1)
                                .content("Test content")
                                .story(testStory)
                                .build();

                testChapterDTO = new ChapterDTO();
                testChapterDTO.setId(1L);
                testChapterDTO.setTitle("Chapter 1");
                testChapterDTO.setChapterNumber(1);
                testChapterDTO.setContent("Test content");
                testChapterDTO.setStoryId(1L);
        }

        @Test
        void createChapter_ShouldReturnCreatedChapter_WhenValidInput() throws Exception {
                when(chapterService.createChapter(any(ChapterDTO.class))).thenReturn(testChapter);

                mockMvc.perform(post("/api/chapters/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testChapterDTO)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Chapter 1"))
                                .andExpect(jsonPath("$.chapterNumber").value(1));
        }

        @Test
        void updateChapter_ShouldReturnUpdatedChapter_WhenChapterExists() throws Exception {
                when(chapterService.updateChapter(anyLong(), any(ChapterDTO.class))).thenReturn(testChapter);

                mockMvc.perform(put("/api/chapters/update/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testChapterDTO)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Chapter 1"));
        }

        @Test
        void deleteChapter_ShouldReturnNoContent_WhenChapterExists() throws Exception {
                doNothing().when(chapterService).deleteChapter(1L);

                mockMvc.perform(delete("/api/chapters/delete/1"))
                                .andExpect(status().isNoContent());

                verify(chapterService, times(1)).deleteChapter(1L);
        }

        @Test
        void getChapterById_ShouldReturnChapter_WhenChapterExists() throws Exception {
                when(chapterService.getChapterById(1L)).thenReturn(Optional.of(testChapter));

                mockMvc.perform(get("/api/chapters/get/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.title").value("Chapter 1"));
        }

        @Test
        void getChaptersByStoryId_ShouldReturnListOfChapters() throws Exception {
                Chapter chapter2 = Chapter.builder()
                                .id(2L)
                                .title("Chapter 2")
                                .chapterNumber(2)
                                .story(testStory)
                                .build();

                List<Chapter> chapters = Arrays.asList(testChapter, chapter2);
                when(chapterService.getChaptersByStoryId(1L)).thenReturn(chapters);

                mockMvc.perform(get("/api/chapters/story/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$.length()").value(2))
                                .andExpect(jsonPath("$[0].title").value("Chapter 1"))
                                .andExpect(jsonPath("$[1].title").value("Chapter 2"));
        }

        @Test
        void getChapterByStoryIdAndNumber_ShouldReturnChapter_WhenExists() throws Exception {
                when(chapterService.getChapterByStoryIdAndNumber(1L, 1)).thenReturn(Optional.of(testChapter));

                mockMvc.perform(get("/api/chapters/story/1/number/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Chapter 1"))
                                .andExpect(jsonPath("$.chapterNumber").value(1));
        }

        @Test
        void searchChaptersByTitle_ShouldReturnMatchingChapters() throws Exception {
                List<Chapter> chapters = Arrays.asList(testChapter);
                when(chapterService.searchChaptersByTitle("Chapter")).thenReturn(chapters);

                mockMvc.perform(get("/api/chapters/search")
                                .param("title", "Chapter"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$.length()").value(1))
                                .andExpect(jsonPath("$[0].title").value("Chapter 1"));
        }

        @Test
        void getAllChapters_ShouldReturnAllChapters() throws Exception {
                List<Chapter> chapters = Arrays.asList(testChapter);
                when(chapterService.getAllChapters()).thenReturn(chapters);

                mockMvc.perform(get("/api/chapters/get_all"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$.length()").value(1));
        }
}
