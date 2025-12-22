package com.search.truyen.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.search.truyen.dtos.HistoryDTO;
import com.search.truyen.model.entities.History;
import com.search.truyen.service.HistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryService historyService;

    @PostMapping("/save")
    public ResponseEntity<HistoryDTO> saveHistory(@RequestBody HistoryDTO historyDTO) {
        History savedHistory = historyService.createOrUpdateHistory(historyDTO);
        return ResponseEntity.ok(mapToDTO(savedHistory));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        historyService.deleteHistory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HistoryDTO> getHistoryById(@PathVariable Long id) {
        History history = historyService.getHistoryById(id)
                .orElseThrow(() -> new RuntimeException("History not found with id: " + id));
        return ResponseEntity.ok(mapToDTO(history));
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<HistoryDTO>> getAllHistories() {
        List<History> histories = historyService.getAllHistories();
        List<HistoryDTO> historyDTOs = histories.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historyDTOs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HistoryDTO>> getHistoriesByUserId(@PathVariable Long userId) {
        List<History> histories = historyService.getHistoriesByUserId(userId);
        List<HistoryDTO> historyDTOs = histories.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historyDTOs);
    }

    @GetMapping("/story/{storyId}")
    public ResponseEntity<List<HistoryDTO>> getHistoriesByStoryId(@PathVariable Long storyId) {
        List<History> histories = historyService.getHistoriesByStoryId(storyId);
        List<HistoryDTO> historyDTOs = histories.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historyDTOs);
    }

    @GetMapping("/user/{userId}/story/{storyId}")
    public ResponseEntity<HistoryDTO> getHistoryByUserIdAndStoryId(
            @PathVariable Long userId,
            @PathVariable Long storyId) {
        History history = historyService.getHistoryByUserIdAndStoryId(userId, storyId)
                .orElseThrow(() -> new RuntimeException("History not found"));
        return ResponseEntity.ok(mapToDTO(history));
    }
}
