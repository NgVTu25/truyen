package com.search.truyen.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final ModelMapper modelMapper ;

    public Chapter createChapter(Long id, ChapterDTO chapterDTO) {
        Story story = storyRepository.findById(chapterDTO.getStoryId())
                .orElseThrow(() -> new RuntimeException("Story not found with id: " + id));
        Chapter chapter = new Chapter();
        modelMapper.map(chapterDTO, chapter);
        return chapterRepository.save(chapter);
    }

    public Chapter updateChapter(Long id, ChapterDTO chapterDTO) {
        Chapter existingChapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found with id: " + id));

        existingChapter.setTitle(chapterDTO.getTitle());
        existingChapter.setChapterNumber(chapterDTO.getChapterNumber());
        existingChapter.setContent(chapterDTO.getContent());
        existingChapter.setPages(chapterDTO.getPages());

        if (chapterDTO.getStoryId() != null) {
            Story story = storyRepository.findById(chapterDTO.getStoryId())
                    .orElseThrow(() -> new RuntimeException("Story not found with id: " + chapterDTO.getStoryId()));
            existingChapter.setStory(story);
        }

        return chapterRepository.save(existingChapter);
    }

    public Page<Chapter> getChaptersByStoryId(Long storyId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("chapterNumber").ascending());
        return chapterRepository.findByStoryId(storyId, pageable);
    }

    public void deleteChapter(Long id) {
        if (!chapterRepository.existsById(id)) {
            throw new RuntimeException("Chapter not found with id: " + id);
        }
        chapterRepository.deleteById(id);
    }

    public Optional<Chapter> getChapterById(Long id) {
        return chapterRepository.findById(id);
    }

    public List<Chapter> getAllChapters() {
        return chapterRepository.findAll();
    }

    public List<Chapter> getChaptersByStoryId(Long storyId) {
        return chapterRepository.findByStoryId(storyId);
    }

    public Optional<Chapter> getChapterByStoryIdAndNumber(Long storyId, int chapterNumber) {
        return chapterRepository.findByStoryIdAndChapterNumber(storyId, chapterNumber);
    }

    public List<Chapter> searchChaptersByTitle(String title) {
        return chapterRepository.findByTitleContainingIgnoreCase(title);
    }
}
