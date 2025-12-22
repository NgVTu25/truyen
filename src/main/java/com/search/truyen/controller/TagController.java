package com.search.truyen.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.search.truyen.service.TagService;
import com.search.truyen.dtos.tagDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @PostMapping("/create")
    public ResponseEntity<tagDTO> createTag(@RequestBody tagDTO tagDto) {
        return ResponseEntity.ok(tagService.createTag(tagDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<tagDTO> updateTag(@PathVariable Long id, @RequestBody tagDTO tagDto) {
        return ResponseEntity.ok(tagService.updateTag(id, tagDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<tagDTO> getTagById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<tagDTO>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }
}