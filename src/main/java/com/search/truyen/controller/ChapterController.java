package com.search.truyen.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.search.truyen.dtos.ChapterDTO;
import com.search.truyen.service.ChapterService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chapters")
public class ChapterController {
    private final ChapterService chapterService;

    @PostMapping("/create/{storyId}")
    public ResponseEntity<ChapterDTO> createChapter(@PathVariable Long storyId, @RequestBody ChapterDTO chapterDTO) {
        return ResponseEntity.ok(chapterService.createChapter(storyId, chapterDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChapterDTO> updateChapter(@PathVariable Long id, @RequestBody ChapterDTO chapterDTO) {
        return ResponseEntity.ok(chapterService.updateChapter(id, chapterDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteChapter(@PathVariable Long id) {
        chapterService.deleteChapter(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ChapterDTO> getChapterById(@PathVariable Long id) {
        return chapterService.getChapterById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<ChapterDTO>> getAllChapters() {
        return ResponseEntity.ok(chapterService.getAllChapters());
    }

    @GetMapping("/story/{storyId}")
    public ResponseEntity<List<ChapterDTO>> getChaptersByStoryId(@PathVariable Long storyId) {
        return ResponseEntity.ok(chapterService.getChaptersByStoryId(storyId));
    }

    @GetMapping("/story/{storyId}/number/{chapterNumber}")
    public ResponseEntity<ChapterDTO> getChapterByStoryIdAndNumber(
            @PathVariable Long storyId, @PathVariable int chapterNumber) {
        return chapterService.getChapterByStoryIdAndNumber(storyId, chapterNumber)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ChapterDTO>> searchChaptersByTitle(@RequestParam String title) {
        return ResponseEntity.ok(chapterService.searchChaptersByTitle(title));
    }
}