package com.search.truyen.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.search.truyen.dtos.ChapterDTO;
import com.search.truyen.model.entities.Chapter;
import com.search.truyen.model.entities.Story;
import com.search.truyen.repository.ChapterRepository;
import com.search.truyen.repository.storyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final storyRepository storyRepository;
    private final ModelMapper modelMapper;

    public ChapterDTO createChapter(Long storyId, ChapterDTO chapterDTO) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));
        Chapter chapter = modelMapper.map(chapterDTO, Chapter.class);
        chapter.setStory(story);
        return modelMapper.map(chapterRepository.save(chapter), ChapterDTO.class);
    }

    public ChapterDTO updateChapter(Long id, ChapterDTO chapterDTO) {
        Chapter existing = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        modelMapper.map(chapterDTO, existing);
        existing.setId(id);
        if (chapterDTO.getStoryId() != null) {
            Story story = storyRepository.findById(chapterDTO.getStoryId())
                    .orElseThrow(() -> new RuntimeException("Story not found"));
            existing.setStory(story);
        }
        return modelMapper.map(chapterRepository.save(existing), ChapterDTO.class);
    }

    public void deleteChapter(Long id) {
        if (!chapterRepository.existsById(id)) throw new RuntimeException("Chapter not found");
        chapterRepository.deleteById(id);
    }

    public Optional<ChapterDTO> getChapterById(Long id) {
        return chapterRepository.findById(id)
                .map(c -> modelMapper.map(c, ChapterDTO.class));
    }

    public List<ChapterDTO> getAllChapters() {
        return chapterRepository.findAll().stream()
                .map(c -> modelMapper.map(c, ChapterDTO.class))
                .collect(Collectors.toList());
    }

    public List<ChapterDTO> getChaptersByStoryId(Long storyId) {
        return chapterRepository.findByStoryId(storyId).stream()
                .map(c -> modelMapper.map(c, ChapterDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<ChapterDTO> getChapterByStoryIdAndNumber(Long storyId, int number) {
        return chapterRepository.findByStoryIdAndChapterNumber(storyId, number)
                .map(c -> modelMapper.map(c, ChapterDTO.class));
    }

    public List<ChapterDTO> searchChaptersByTitle(String title) {
        return chapterRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(c -> modelMapper.map(c, ChapterDTO.class))
                .collect(Collectors.toList());
    }
}