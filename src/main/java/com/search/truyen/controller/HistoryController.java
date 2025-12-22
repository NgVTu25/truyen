package com.search.truyen.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.search.truyen.dtos.HistoryDTO;
import com.search.truyen.service.HistoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class HistoryController {
    private final HistoryService historyService;

    @PostMapping("/save")
    public ResponseEntity<HistoryDTO> saveHistory(@RequestBody HistoryDTO historyDTO) {
        return ResponseEntity.ok(historyService.createOrUpdateHistory(historyDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {
        historyService.deleteHistory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HistoryDTO> getHistoryById(@PathVariable Long id) {
        return historyService.getHistoryById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("History not found"));
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<HistoryDTO>> getAllHistories() {
        return ResponseEntity.ok(historyService.getAllHistories());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HistoryDTO>> getHistoriesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(historyService.getHistoriesByUserId(userId));
    }

    @GetMapping("/story/{storyId}")
    public ResponseEntity<List<HistoryDTO>> getHistoriesByStoryId(@PathVariable Long storyId) {
        return ResponseEntity.ok(historyService.getHistoriesByStoryId(storyId));
    }

    @GetMapping("/user/{userId}/story/{storyId}")
    public ResponseEntity<HistoryDTO> getHistoryByUserIdAndStoryId(
            @PathVariable Long userId, @PathVariable Long storyId) {
        return historyService.getHistoryByUserIdAndStoryId(userId, storyId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("History not found"));
    }
}