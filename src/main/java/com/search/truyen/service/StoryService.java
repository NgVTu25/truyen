package com.search.truyen.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.search.truyen.dtos.storyDTO;
import com.search.truyen.model.entities.Story;
import com.search.truyen.repository.storyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryService {
    private final storyRepository storyRepository;
    private final ModelMapper modelMapper;

    public storyDTO createStory(storyDTO storyDto) {
        Story story = modelMapper.map(storyDto, Story.class);
        Story savedStory = storyRepository.save(story);
        return modelMapper.map(savedStory, storyDTO.class);
    }

    public storyDTO updateStory(Long id, storyDTO storyDto) {
        Story existingStory = storyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Story not found with id: " + id));
        modelMapper.map(storyDto, existingStory);
        existingStory.setId(id); // Đảm bảo ID không bị ghi đè
        return modelMapper.map(storyRepository.save(existingStory), storyDTO.class);
    }

    public void deleteStory(Long id) {
        if (!storyRepository.existsById(id)) throw new RuntimeException("Story not found");
        storyRepository.deleteById(id);
    }

    public Optional<storyDTO> getStoryById(Long id) {
        return storyRepository.findById(id)
                .map(story -> modelMapper.map(story, storyDTO.class));
    }

    public Optional<storyDTO> getStoryByTitle(String title) {
        return storyRepository.findFirstByTitleContainingIgnoreCase(title)
                .map(story -> modelMapper.map(story, storyDTO.class));
    }

    public Page<storyDTO> getAllStories(int page, int size) {
        return storyRepository.findAll(PageRequest.of(page, size))
                .map(story -> modelMapper.map(story, storyDTO.class));
    }
}