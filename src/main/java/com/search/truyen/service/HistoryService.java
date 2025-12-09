package com.search.truyen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.search.truyen.dtos.HistoryDTO;
import com.search.truyen.model.entities.Chapter;
import com.search.truyen.model.entities.History;
import com.search.truyen.model.entities.Story;
import com.search.truyen.model.entities.User;
import com.search.truyen.repository.ChapterRepository;
import com.search.truyen.repository.HistoryRepository;
import com.search.truyen.repository.UserRepository;
import com.search.truyen.repository.storyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final storyRepository storyRepository;
    private final ChapterRepository chapterRepository;

    public History createOrUpdateHistory(HistoryDTO historyDTO) {
        User user = userRepository.findById(historyDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + historyDTO.getUserId()));

        Story story = storyRepository.findById(historyDTO.getStoryId())
                .orElseThrow(() -> new RuntimeException("Story not found with id: " + historyDTO.getStoryId()));

        Chapter chapter = chapterRepository.findById(historyDTO.getChapterId())
                .orElseThrow(() -> new RuntimeException("Chapter not found with id: " + historyDTO.getChapterId()));

        // Tìm xem đã có history cho user và story này chưa
        Optional<History> existingHistory = historyRepository.findByUserIdAndStoryId(
                historyDTO.getUserId(), historyDTO.getStoryId());

        History history;
        if (existingHistory.isPresent()) {
            // Update existing history
            history = existingHistory.get();
            history.setChapter(chapter);
            history.setLastPage(historyDTO.getLastPage());
        } else {
            // Create new history
            history = History.builder()
                    .user(user)
                    .story(story)
                    .chapter(chapter)
                    .lastPage(historyDTO.getLastPage())
                    .build();
        }

        return historyRepository.save(history);
    }

    public void deleteHistory(Long id) {
        if (!historyRepository.existsById(id)) {
            throw new RuntimeException("History not found with id: " + id);
        }
        historyRepository.deleteById(id);
    }

    public Optional<History> getHistoryById(Long id) {
        return historyRepository.findById(id);
    }

    public List<History> getAllHistories() {
        return historyRepository.findAll();
    }

    public List<History> getHistoriesByUserId(Long userId) {
        return historyRepository.findByUserIdOrderByLastReadDesc(userId);
    }

    public List<History> getHistoriesByStoryId(Long storyId) {
        return historyRepository.findByStoryId(storyId);
    }

    public Optional<History> getHistoryByUserIdAndStoryId(Long userId, Long storyId) {
        return historyRepository.findByUserIdAndStoryId(userId, storyId);
    }
}
