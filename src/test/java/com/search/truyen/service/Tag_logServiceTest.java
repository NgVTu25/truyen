package com.search.truyen.service;

import com.search.truyen.dtos.TaglogDTO;
import com.search.truyen.model.entities.Story;
import com.search.truyen.model.entities.Tag_log;
import com.search.truyen.repository.tag_logRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Tag_logServiceTest {

    @Mock
    private tag_logRepository tag_logRepository;

    @InjectMocks
    private Tag_logService tag_logService;

    private Tag_log testTagLog;
    private TaglogDTO testTagLogDTO;
    private Story testStory;

    @BeforeEach
    void setUp() {
        testStory = Story.builder()
                .id(1L)
                .title("Test Story")
                .build();

        testTagLog = Tag_log.builder()
                .id(1L)
                .story(testStory)
                .summary("Test summary")
                .generated_tag("action, adventure")
                .build();

        testTagLogDTO = new TaglogDTO();
        testTagLogDTO.setId(1L);
        testTagLogDTO.setStory(testStory);
        testTagLogDTO.setSummary("Test summary");
        testTagLogDTO.setGenerated_tag("action, adventure");
    }

    @Test
    void createTag_log_ShouldCreateTagLog_WhenValidInput() {
        when(tag_logRepository.save(any(Tag_log.class))).thenReturn(testTagLog);

        Tag_log result = tag_logService.createTag_log(testTagLogDTO);

        assertThat(result).isNotNull();
        assertThat(result.getSummary()).isEqualTo("Test summary");
        assertThat(result.getGenerated_tag()).isEqualTo("action, adventure");
        verify(tag_logRepository, times(1)).save(any(Tag_log.class));
    }

    @Test
    void updateTag_log_ShouldUpdateTagLog_WhenTagLogExists() {
        when(tag_logRepository.findById(1L)).thenReturn(Optional.of(testTagLog));
        when(tag_logRepository.save(any(Tag_log.class))).thenReturn(testTagLog);

        testTagLogDTO.setSummary("Updated summary");
        Tag_log result = tag_logService.updateTag_log(testTagLogDTO);

        assertThat(result).isNotNull();
        verify(tag_logRepository, times(1)).findById(1L);
        verify(tag_logRepository, times(1)).save(any(Tag_log.class));
    }

    @Test
    void updateTag_log_ShouldThrowException_WhenTagLogNotFound() {
        when(tag_logRepository.findById(999L)).thenReturn(Optional.empty());
        testTagLogDTO.setId(999L);

        assertThatThrownBy(() -> tag_logService.updateTag_log(testTagLogDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Tag_log not found");
    }

    @Test
    void deleteTag_log_ShouldDeleteTagLog_WhenTagLogExists() {
        when(tag_logRepository.existsById(1L)).thenReturn(true);
        doNothing().when(tag_logRepository).deleteById(1L);

        tag_logService.deleteTag_log(testTagLogDTO);

        verify(tag_logRepository, times(1)).existsById(1L);
        verify(tag_logRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTag_log_ShouldThrowException_WhenTagLogNotFound() {
        when(tag_logRepository.existsById(999L)).thenReturn(false);
        testTagLogDTO.setId(999L);

        assertThatThrownBy(() -> tag_logService.deleteTag_log(testTagLogDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Tag_log not found");
    }

    @Test
    void getTag_logById_ShouldReturnTagLog_WhenTagLogExists() {
        when(tag_logRepository.findById(1L)).thenReturn(Optional.of(testTagLog));

        Tag_log result = tag_logService.getTag_logById(testTagLogDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getSummary()).isEqualTo("Test summary");
    }

    @Test
    void getTag_logById_ShouldReturnNull_WhenTagLogNotFound() {
        when(tag_logRepository.findById(999L)).thenReturn(Optional.empty());
        testTagLogDTO.setId(999L);

        Tag_log result = tag_logService.getTag_logById(testTagLogDTO);

        assertThat(result).isNull();
    }

    @Test
    void getAllTag_logs_ShouldReturnListOfTagLogs() {
        Tag_log tagLog2 = Tag_log.builder()
                .id(2L)
                .summary("Summary 2")
                .generated_tag("fantasy")
                .build();

        List<Tag_log> tagLogs = Arrays.asList(testTagLog, tagLog2);
        when(tag_logRepository.findAll()).thenReturn(tagLogs);

        List<Tag_log> result = tag_logService.getAllTag_logs();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getSummary()).isEqualTo("Test summary");
        assertThat(result.get(1).getSummary()).isEqualTo("Summary 2");
    }
}
