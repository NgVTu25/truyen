package com.search.truyen.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.search.truyen.dtos.ChapterDTO;
import com.search.truyen.model.entities.Chapter;
import com.search.truyen.service.ChapterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chapters")
public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping("/create/{id}")
    public ResponseEntity<ChapterDTO> createChapter(@RequestBody @PathVariable Long id, ChapterDTO chapterDTO) {
        Chapter createdChapter = chapterService.createChapter(id, chapterDTO);
        return ResponseEntity.ok(mapToDTO(createdChapter));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChapterDTO> updateChapter(@PathVariable Long id, @RequestBody ChapterDTO chapterDTO) {
        Chapter updatedChapter = chapterService.updateChapter(id, chapterDTO);
        return ResponseEntity.ok(mapToDTO(updatedChapter));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteChapter(@PathVariable Long id) {
        chapterService.deleteChapter(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ChapterDTO> getChapterById(@PathVariable Long id) {
        Chapter chapter = chapterService.getChapterById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found with id: " + id));
        return ResponseEntity.ok(mapToDTO(chapter));
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<ChapterDTO>> getAllChapters() {
        List<Chapter> chapters = chapterService.getAllChapters();
        List<ChapterDTO> chapterDTOs = chapters.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(chapterDTOs);
    }

    @GetMapping("/story/{storyId}")
    public ResponseEntity<List<ChapterDTO>> getChaptersByStoryId(@PathVariable Long storyId) {
        List<Chapter> chapters = chapterService.getChaptersByStoryId(storyId);
        List<ChapterDTO> chapterDTOs = chapters.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(chapterDTOs);
    }

    @GetMapping("/story/{storyId}/number/{chapterNumber}")
    public ResponseEntity<ChapterDTO> getChapterByStoryIdAndNumber(
            @PathVariable Long storyId,
            @PathVariable int chapterNumber) {
        Chapter chapter = chapterService.getChapterByStoryIdAndNumber(storyId, chapterNumber)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        return ResponseEntity.ok(mapToDTO(chapter));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ChapterDTO>> searchChaptersByTitle(@RequestParam String title) {
        List<Chapter> chapters = chapterService.searchChaptersByTitle(title);
        List<ChapterDTO> chapterDTOs = chapters.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(chapterDTOs);
    }

    private ChapterDTO mapToDTO(Chapter chapter) {
        if (chapter == null) {
            return null;
        }

        ChapterDTO dto = new ChapterDTO();
        dto.setId(chapter.getId());
        dto.setTitle(chapter.getTitle());
        dto.setChapterNumber(chapter.getChapterNumber());
        dto.setContent(chapter.getContent());
        dto.setPages(chapter.getPages());

        if (chapter.getStory() != null) {
            dto.setStoryId(chapter.getStory().getId());
        }

        return dto;
    }
}
