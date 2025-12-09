package com.search.truyen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.truyen.dtos.tagDTO;
import com.search.truyen.model.entities.Tag;
import com.search.truyen.service.TagService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TagController.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TagService tagService;

    private Tag testTag;
    private tagDTO testTagDTO;

    @BeforeEach
    void setUp() {
        testTag = Tag.builder()
                .id(1L)
                .name("Action")
                .build();

        testTagDTO = new tagDTO();
        testTagDTO.setId(1L);
        testTagDTO.setName("Action");
    }

    @Test
    void createTag_ShouldReturnCreatedTag_WhenValidInput() throws Exception {
        when(tagService.createTag(any(tagDTO.class))).thenReturn(testTag);

        mockMvc.perform(post("/api/tags/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTagDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Action"));
    }

    @Test
    void updateTag_ShouldReturnUpdatedTag_WhenTagExists() throws Exception {
        when(tagService.updateTag(anyLong(), any(tagDTO.class))).thenReturn(testTag);

        mockMvc.perform(put("/api/tags/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTagDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Action"));
    }

    @Test
    void deleteTag_ShouldReturnOk_WhenTagExists() throws Exception {
        doNothing().when(tagService).deleteTag(1L);

        mockMvc.perform(delete("/api/tags/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTagDTO)))
                .andExpect(status().isOk());

        verify(tagService, times(1)).deleteTag(1L);
    }

    @Test
    void getTagById_ShouldReturnTag_WhenTagExists() throws Exception {
        when(tagService.getTagById(1L)).thenReturn(testTag);

        mockMvc.perform(get("/api/tags/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Action"));
    }

    @Test
    void getAllTags_ShouldReturnListOfTags() throws Exception {
        Tag tag2 = Tag.builder().id(2L).name("Adventure").build();
        List<Tag> tags = Arrays.asList(testTag, tag2);
        when(tagService.getAllTags()).thenReturn(tags);

        mockMvc.perform(get("/api/tags/get_all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Action"))
                .andExpect(jsonPath("$[1].name").value("Adventure"));
    }
}
