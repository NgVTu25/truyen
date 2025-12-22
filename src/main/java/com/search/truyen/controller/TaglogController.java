package com.search.truyen.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.search.truyen.dtos.TaglogDTO;
import com.search.truyen.service.Tag_logService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tag_log")
public class TaglogController {
    private final Tag_logService tag_logService;

    @PostMapping("/create")
    public ResponseEntity<TaglogDTO> createTag_log(@RequestBody TaglogDTO taglogDTO) {
        return ResponseEntity.ok(tag_logService.createTag_log(taglogDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaglogDTO> updateTag_log(@PathVariable Long id, @RequestBody TaglogDTO taglogDTO) {
        return ResponseEntity.ok(tag_logService.updateTag_log(id, taglogDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTag_log(@PathVariable Long id) {
        tag_logService.deleteTag_log(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TaglogDTO> getTag_logById(@PathVariable Long id) {
        return tag_logService.getTag_logById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Tag_log not found"));
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<TaglogDTO>> getAllTag_logs() {
        return ResponseEntity.ok(tag_logService.getAllTag_logs());
    }
}