package com.search.truyen.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.search.truyen.dtos.HistoryDTO;
import com.search.truyen.model.entities.*;
import com.search.truyen.repository.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final storyRepository storyRepository;
    private final ChapterRepository chapterRepository;
    private final ModelMapper modelMapper;

    public HistoryDTO createOrUpdateHistory(HistoryDTO historyDTO) {
        User user = userRepository.findById(historyDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Story story = storyRepository.findById(historyDTO.getStoryId())
                .orElseThrow(() -> new RuntimeException("Story not found"));
        Chapter chapter = chapterRepository.findById(historyDTO.getChapterId())
                .orElseThrow(() -> new RuntimeException("Chapter not found"));

        Optional<History> existingHistory = historyRepository.findByUserIdAndStoryId(
                historyDTO.getUserId(), historyDTO.getStoryId());

        History history = existingHistory.orElseGet(History::new);
        if (history.getId() == null) {
            history.setUser(user);
            history.setStory(story);
        }

        history.setChapter(chapter);
        history.setLastPage(historyDTO.getLastPage());

        return modelMapper.map(historyRepository.save(history), HistoryDTO.class);
    }

    public void deleteHistory(Long id) {
        if (!historyRepository.existsById(id)) throw new RuntimeException("History not found");
        historyRepository.deleteById(id);
    }

    public Optional<HistoryDTO> getHistoryById(Long id) {
        return historyRepository.findById(id)
                .map(h -> modelMapper.map(h, HistoryDTO.class));
    }

    public List<HistoryDTO> getAllHistories() {
        return historyRepository.findAll().stream()
                .map(h -> modelMapper.map(h, HistoryDTO.class))
                .collect(Collectors.toList());
    }

    public List<HistoryDTO> getHistoriesByUserId(Long userId) {
        return historyRepository.findByUserIdOrderByLastReadDesc(userId).stream()
                .map(h -> modelMapper.map(h, HistoryDTO.class))
                .collect(Collectors.toList());
    }

    public List<HistoryDTO> getHistoriesByStoryId(Long storyId) {
        return historyRepository.findByStoryId(storyId).stream()
                .map(h -> modelMapper.map(h, HistoryDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<HistoryDTO> getHistoryByUserIdAndStoryId(Long userId, Long storyId) {
        return historyRepository.findByUserIdAndStoryId(userId, storyId)
                .map(h -> modelMapper.map(h, HistoryDTO.class));
    }
}