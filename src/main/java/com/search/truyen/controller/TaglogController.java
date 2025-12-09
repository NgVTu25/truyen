package com.search.truyen.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

import com.search.truyen.dtos.TaglogDTO;
import com.search.truyen.model.entities.Tag_log;
import com.search.truyen.service.Tag_logService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tag_log")
public class TaglogController {
    private final Tag_logService tag_logService;

    @PostMapping("/create")
    public Tag_log createTag_log(@RequestBody TaglogDTO tag_log) {
        return tag_logService.createTag_log(tag_log);
    }

    @PutMapping("/update/{id}")
    public Tag_log updateTag_log(@RequestBody TaglogDTO tag_log) {
        return tag_logService.updateTag_log(tag_log);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTag_log(@RequestBody TaglogDTO tag_log) {
        tag_logService.deleteTag_log(tag_log);
    }

    @GetMapping("/get/{id}")
    public Tag_log getTag_logById(@PathVariable Long id) {
        TaglogDTO dto = new TaglogDTO();
        dto.setId(id);
        return tag_logService.getTag_logById(dto);
    }

    @GetMapping("/get_all")
    public List<Tag_log> getAllTag_logs() {
        return tag_logService.getAllTag_logs();
    }
}
