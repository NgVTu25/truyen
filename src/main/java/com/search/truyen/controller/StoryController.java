package com.search.truyen.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.search.truyen.dtos.storyDTO;
import com.search.truyen.model.entities.Story;
import com.search.truyen.service.StoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryService storyService;

    @PostMapping("/create")
    public ResponseEntity<storyDTO> createStory(@RequestBody storyDTO storyDto) {
        Story createdStory = storyService.createStory(storyDto);
        return ResponseEntity.ok(mapToDTO(createdStory));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<storyDTO> updateStory(@PathVariable Long id, @RequestBody storyDTO storyDto) {
        Story story = storyService.getStoryById(id)
                .orElseThrow(() -> new RuntimeException("Story not found with id: " + id));

        // Update fields
        story.setTitle(storyDto.getTitle());
        story.setDescription(storyDto.getDescription());
        story.setChapters(storyDto.getChapters());
        story.setTags(storyDto.getTags());
        story.setCoverImage(storyDto.getCoverImage());
        story.setType(storyDto.getType());

        Story updatedStory = storyService.updateStory(story);
        return ResponseEntity.ok(mapToDTO(updatedStory));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<storyDTO> getStoryById(@PathVariable Long id) {
        Story story = storyService.getStoryById(id)
                .orElseThrow(() -> new RuntimeException("Story not found with id: " + id));
        return ResponseEntity.ok(mapToDTO(story));
    }

    @GetMapping("/search")
    public ResponseEntity<storyDTO> getStoryByTitle(@RequestParam String title) {
        Story story = storyService.getStoryByTitle(title)
                .orElseThrow(() -> new RuntimeException("Story not found with title: " + title));
        return ResponseEntity.ok(mapToDTO(story));
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<storyDTO>> getAllStories() {
        List<Story> stories = storyService.getAllStories();
        List<storyDTO> storyDTOs = stories.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(storyDTOs);
    }

    private storyDTO mapToDTO(Story story) {
        if (story == null) {
            return null;
        }

        storyDTO dto = new storyDTO();
        dto.setId(story.getId());
        dto.setTitle(story.getTitle());
        dto.setDescription(story.getDescription());
        dto.setChapters(story.getChapters());
        dto.setTags(story.getTags());
        dto.setCoverImage(story.getCoverImage());
        dto.setType(story.getType());
        return dto;
    }
}
