package com.search.truyen.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.search.truyen.service.TagService;
import com.search.truyen.dtos.tagDTO;
import com.search.truyen.model.entities.Tag;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @PostMapping("/create")
    public tagDTO createTag(@RequestBody tagDTO tagDtoInput) {
        Tag createdTag = tagService.createTag(tagDtoInput);
        return mapToDTO(createdTag);
    }

    @PutMapping("/update/{id}")
    public tagDTO updateTag(@RequestBody tagDTO tagDtoInput) {
        Tag updatedTag = tagService.updateTag(tagDtoInput.getId(), tagDtoInput);

        return mapToDTO(updatedTag);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTag(@RequestBody tagDTO tagDtoInput) {
        tagService.deleteTag(tagDtoInput.getId());
    }

    @GetMapping("/get/{id}")
    public tagDTO getTagById(@PathVariable Long id) {
        Tag tagEntity = tagService.getTagById(id);
        return mapToDTO(tagEntity);
    }

    @GetMapping("/get_all")
    public List<tagDTO> getAllTags() {
        List<Tag> listTags = tagService.getAllTags();
        return listTags.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private tagDTO mapToDTO(Tag tagEntity) {
        if (tagEntity == null)
            return null;

        tagDTO dto = new tagDTO();
        dto.setId(tagEntity.getId());
        dto.setName(tagEntity.getName());
        return dto;
    }
}