package com.search.truyen.service;

import java.util.List;
import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.search.truyen.repository.storyRepository;
import com.search.truyen.model.entities.Story;
import com.search.truyen.dtos.storyDTO;

@Service
@RequiredArgsConstructor
public class StoryService {
    private final storyRepository storyRepository;
    private final ModelMapper modelMapper;

    public Story createStory(storyDTO storyDto) {
        Story story = new Story();
        modelMapper.map(storyDto, story);
        return storyRepository.save(story);
    }

    public Optional<Story> getStoryById( Long id) {
        return storyRepository.findById(id);
    }

    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    public Optional<Story> getStoryByTitle(String title) {
        return storyRepository.findFirstByTitleContainingIgnoreCase(title);
    }

    public Story updateStory(Long id, storyDTO story) {
        Story updatedStory = storyRepository.findById(id).orElseThrow(() ->  new RuntimeException("Story Not Found"));
        modelMapper.map(story, updatedStory);

        return storyRepository.save(updatedStory);
    }

    public void deleteStory( Long id) {
        storyRepository.deleteById(id);
    }
}
