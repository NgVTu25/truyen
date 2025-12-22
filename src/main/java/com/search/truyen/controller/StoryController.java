package com.search.truyen.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.search.truyen.dtos.storyDTO;
import com.search.truyen.service.StoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryService storyService;

    @PostMapping("/create")
    public ResponseEntity<storyDTO> createStory(@RequestBody storyDTO storyDto) {
        return ResponseEntity.ok(storyService.createStory(storyDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<storyDTO> updateStory(@PathVariable Long id, @RequestBody storyDTO storyDto) {
        return ResponseEntity.ok(storyService.updateStory(id, storyDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<storyDTO> getStoryById(@PathVariable Long id) {
        return storyService.getStoryById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Story not found"));
    }

    @GetMapping("/search")
    public ResponseEntity<storyDTO> getStoryByTitle(@RequestParam String title) {
        return storyService.getStoryByTitle(title)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Story not found"));
    }

    @GetMapping("/get_all")
    public ResponseEntity<Page<storyDTO>> getAllStories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(storyService.getAllStories(page, size));
    }
}