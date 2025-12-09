package com.search.truyen.service;

import java.util.List;
import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.search.truyen.repository.storyRepository;
import com.search.truyen.model.entities.Story;
import com.search.truyen.dtos.storyDTO;

@Service
@RequiredArgsConstructor
public class StoryService {
    private final storyRepository storyRepository;

    public Story createStory(@NonNull storyDTO storyDto) {
        Story story = Story.builder()
                .title(storyDto.getTitle())
                .description(storyDto.getDescription())
                .chapters(storyDto.getChapters())
                .tags(storyDto.getTags())
                .coverImage(storyDto.getCoverImage())
                .type(storyDto.getType())
                .build();
        return java.util.Objects.requireNonNull(storyRepository.save(story));
    }

    public Optional<Story> getStoryById(@NonNull Long id) {
        return storyRepository.findById(id);
    }

    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    public Optional<Story> getStoryByTitle(String title) {
        return storyRepository.findFirstByTitleContainingIgnoreCase(title);
    }

    public Story updateStory(@NonNull Story story) {
        return java.util.Objects.requireNonNull(storyRepository.save(story));
    }

    public void deleteStory(@NonNull Long id) {
        storyRepository.deleteById(id);
    }
}
