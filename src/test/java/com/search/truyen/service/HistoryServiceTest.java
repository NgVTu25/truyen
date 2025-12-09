package com.search.truyen.service;

import com.search.truyen.dtos.HistoryDTO;
import com.search.truyen.model.entities.Chapter;
import com.search.truyen.model.entities.History;
import com.search.truyen.model.entities.Story;
import com.search.truyen.model.entities.User;
import com.search.truyen.repository.ChapterRepository;
import com.search.truyen.repository.HistoryRepository;
import com.search.truyen.repository.UserRepository;
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
class HistoryServiceTest {

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private storyRepository storyRepository;

    @Mock
    private ChapterRepository chapterRepository;

    @InjectMocks
    private HistoryService historyService;

    private User testUser;
    private Story testStory;
    private Chapter testChapter;
    private History testHistory;
    private HistoryDTO testHistoryDTO;

    @BeforeEach
    void setUp() {
        testUser = User.builder().id(1L).username("testuser").build();
        testStory = Story.builder().id(1L).title("Test Story").build();
        testChapter = Chapter.builder().id(1L).title("Chapter 1").build();

        testHistory = History.builder()
                .id(1L)
                .user(testUser)
                .story(testStory)
                .chapter(testChapter)
                .lastPage(10)
                .build();

        testHistoryDTO = new HistoryDTO();
        testHistoryDTO.setUserId(1L);
        testHistoryDTO.setStoryId(1L);
        testHistoryDTO.setChapterId(1L);
        testHistoryDTO.setLastPage(10);
    }

    @Test
    void createOrUpdateHistory_ShouldCreateNewHistory_WhenNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(storyRepository.findById(1L)).thenReturn(Optional.of(testStory));
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(testChapter));
        when(historyRepository.findByUserIdAndStoryId(1L, 1L)).thenReturn(Optional.empty());
        when(historyRepository.save(any(History.class))).thenReturn(testHistory);

        History result = historyService.createOrUpdateHistory(testHistoryDTO);

        assertThat(result).isNotNull();
        assertThat(result.getLastPage()).isEqualTo(10);
        verify(historyRepository, times(1)).save(any(History.class));
    }

    @Test
    void createOrUpdateHistory_ShouldUpdateExistingHistory_WhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(storyRepository.findById(1L)).thenReturn(Optional.of(testStory));
        when(chapterRepository.findById(1L)).thenReturn(Optional.of(testChapter));
        when(historyRepository.findByUserIdAndStoryId(1L, 1L)).thenReturn(Optional.of(testHistory));
        when(historyRepository.save(any(History.class))).thenReturn(testHistory);

        testHistoryDTO.setLastPage(20);
        History result = historyService.createOrUpdateHistory(testHistoryDTO);

        assertThat(result).isNotNull();
        verify(historyRepository, times(1)).findByUserIdAndStoryId(1L, 1L);
        verify(historyRepository, times(1)).save(any(History.class));
    }

    @Test
    void createOrUpdateHistory_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        testHistoryDTO.setUserId(999L);

        assertThatThrownBy(() -> historyService.createOrUpdateHistory(testHistoryDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void deleteHistory_ShouldDeleteHistory_WhenHistoryExists() {
        when(historyRepository.existsById(1L)).thenReturn(true);
        doNothing().when(historyRepository).deleteById(1L);

        historyService.deleteHistory(1L);

        verify(historyRepository, times(1)).existsById(1L);
        verify(historyRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteHistory_ShouldThrowException_WhenHistoryNotFound() {
        when(historyRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> historyService.deleteHistory(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("History not found");
    }

    @Test
    void getHistoriesByUserId_ShouldReturnListOfHistories() {
        List<History> histories = Arrays.asList(testHistory);
        when(historyRepository.findByUserIdOrderByLastReadDesc(1L)).thenReturn(histories);

        List<History> result = historyService.getHistoriesByUserId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getId()).isEqualTo(1L);
    }

    @Test
    void getHistoryByUserIdAndStoryId_ShouldReturnHistory_WhenExists() {
        when(historyRepository.findByUserIdAndStoryId(1L, 1L)).thenReturn(Optional.of(testHistory));

        Optional<History> result = historyService.getHistoryByUserIdAndStoryId(1L, 1L);

        assertThat(result).isPresent();
        assertThat(result.get().getUser().getId()).isEqualTo(1L);
        assertThat(result.get().getStory().getId()).isEqualTo(1L);
    }
}
