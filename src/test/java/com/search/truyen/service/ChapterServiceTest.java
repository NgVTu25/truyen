package com.search.truyen.service;

import com.search.truyen.dtos.ChapterDTO;
import com.search.truyen.model.entities.Chapter;
import com.search.truyen.model.entities.Story;
import com.search.truyen.repository.ChapterRepository;
import com.search.truyen.repository.storyRepository;
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
class ChapterServiceTest {

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private storyRepository storyRepository;

    @InjectMocks
    private ChapterService chapterService;

    private Story testStory;
    private Chapter testChapter;
    private ChapterDTO testChapterDTO;

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
        testChapterDTO.setTitle("Chapter 1");
        testChapterDTO.setChapterNumber(1);
        testChapterDTO.setContent("Test content");
        testChapterDTO.setStoryId(1L);
    }

    @Test
    void createChapter_ShouldCreateChapter_WhenStoryExists() {
        when(storyRepository.findById(1L)).thenReturn(Optional.of(testStory));
        when(chapterRepository.save(any(Chapter.class))).thenReturn(testChapter);

        Chapter result = chapterService.createChapter(testChapterDTO);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Chapter 1");
        assertThat(result.getChapterNumber()).isEqualTo(1);
        verify(storyRepository, times(1)).findById(1L);
        verify(chapterRepository, times(1)).save(any(Chapter.class));
    }

    @Test
    void createChapter_ShouldThrowException_WhenStoryNotFound() {
        when(storyRepository.findById(999L)).thenReturn(Optional.empty());
        testChapterDTO.setStoryId(999L);

        assertThatThrownBy(() -> chapterService.createChapter(testChapterDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Story not found");
    }

    @Test
    void updateChapter_ShouldUpdateChapter_WhenChapterExists() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(testChapter));
        when(chapterRepository.save(any(Chapter.class))).thenReturn(testChapter);

        testChapterDTO.setTitle("Updated Chapter");
        Chapter result = chapterService.updateChapter(1L, testChapterDTO);

        assertThat(result).isNotNull();
        verify(chapterRepository, times(1)).findById(1L);
        verify(chapterRepository, times(1)).save(any(Chapter.class));
    }

    @Test
    void updateChapter_ShouldThrowException_WhenChapterNotFound() {
        when(chapterRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> chapterService.updateChapter(999L, testChapterDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Chapter not found");
    }

    @Test
    void deleteChapter_ShouldDeleteChapter_WhenChapterExists() {
        when(chapterRepository.existsById(1L)).thenReturn(true);
        doNothing().when(chapterRepository).deleteById(1L);

        chapterService.deleteChapter(1L);

        verify(chapterRepository, times(1)).existsById(1L);
        verify(chapterRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteChapter_ShouldThrowException_WhenChapterNotFound() {
        when(chapterRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> chapterService.deleteChapter(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Chapter not found");
    }

    @Test
    void getChapterById_ShouldReturnChapter_WhenChapterExists() {
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(testChapter));

        Optional<Chapter> result = chapterService.getChapterById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    void getChaptersByStoryId_ShouldReturnListOfChapters() {
        List<Chapter> chapters = Arrays.asList(testChapter);
        when(chapterRepository.findByStoryId(1L)).thenReturn(chapters);

        List<Chapter> result = chapterService.getChaptersByStoryId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Chapter 1");
    }

    @Test
    void getChapterByStoryIdAndNumber_ShouldReturnChapter_WhenExists() {
        when(chapterRepository.findByStoryIdAndChapterNumber(1L, 1)).thenReturn(Optional.of(testChapter));

        Optional<Chapter> result = chapterService.getChapterByStoryIdAndNumber(1L, 1);

        assertThat(result).isPresent();
        assertThat(result.get().getChapterNumber()).isEqualTo(1);
    }

    @Test
    void searchChaptersByTitle_ShouldReturnMatchingChapters() {
        List<Chapter> chapters = Arrays.asList(testChapter);
        when(chapterRepository.findByTitleContainingIgnoreCase("Chapter")).thenReturn(chapters);

        List<Chapter> result = chapterService.searchChaptersByTitle("Chapter");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).contains("Chapter");
    }
}
