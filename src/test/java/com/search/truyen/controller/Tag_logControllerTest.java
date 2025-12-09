package com.search.truyen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.truyen.dtos.TaglogDTO;
import com.search.truyen.model.entities.Story;
import com.search.truyen.model.entities.Tag_log;
import com.search.truyen.service.Tag_logService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaglogController.class)
class TaglogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Tag_logService tag_logService;

    private Tag_log testTagLog;
    private TaglogDTO testTagLogDTO;

    @BeforeEach
    void setUp() {
        Story story = Story.builder().id(1L).title("Test Story").build();

        testTagLog = Tag_log.builder()
                .id(1L)
                .story(story)
                .summary("Test summary")
                .generated_tag("action, adventure")
                .build();

        testTagLogDTO = new TaglogDTO();
        testTagLogDTO.setId(1L);
        testTagLogDTO.setStory(story);
        testTagLogDTO.setSummary("Test summary");
        testTagLogDTO.setGenerated_tag("action, adventure");
    }

    @Test
    void createTag_log_ShouldReturnCreatedTagLog_WhenValidInput() throws Exception {
        when(tag_logService.createTag_log(any(TaglogDTO.class))).thenReturn(testTagLog);

        mockMvc.perform(post("/api/tag_log/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTagLogDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.summary").value("Test summary"))
                .andExpect(jsonPath("$.generated_tag").value("action, adventure"));
    }

    @Test
    void updateTag_log_ShouldReturnUpdatedTagLog_WhenTagLogExists() throws Exception {
        when(tag_logService.updateTag_log(any(TaglogDTO.class))).thenReturn(testTagLog);

        mockMvc.perform(put("/api/tag_log/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTagLogDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.summary").value("Test summary"));
    }

    @Test
    void deleteTag_log_ShouldReturnOk_WhenTagLogExists() throws Exception {
        doNothing().when(tag_logService).deleteTag_log(any(TaglogDTO.class));

        mockMvc.perform(delete("/api/tag_log/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTagLogDTO)))
                .andExpect(status().isOk());

        verify(tag_logService, times(1)).deleteTag_log(any(TaglogDTO.class));
    }

    @Test
    void getTag_logById_ShouldReturnTagLog_WhenTagLogExists() throws Exception {
        when(tag_logService.getTag_logById(any(TaglogDTO.class))).thenReturn(testTagLog);

        mockMvc.perform(get("/api/tag_log/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.summary").value("Test summary"));
    }

    @Test
    void getAllTag_logs_ShouldReturnListOfTagLogs() throws Exception {
        Tag_log tagLog2 = Tag_log.builder()
                .id(2L)
                .summary("Summary 2")
                .generated_tag("fantasy")
                .build();

        List<Tag_log> tagLogs = Arrays.asList(testTagLog, tagLog2);
        when(tag_logService.getAllTag_logs()).thenReturn(tagLogs);

        mockMvc.perform(get("/api/tag_log/get_all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].summary").value("Test summary"))
                .andExpect(jsonPath("$[1].summary").value("Summary 2"));
    }
}
