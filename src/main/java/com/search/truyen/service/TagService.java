package com.search.truyen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.search.truyen.dtos.tagDTO;
import com.search.truyen.model.entities.Tag;
import com.search.truyen.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag createTag(tagDTO tagDTO) {
        Tag tag = Tag.builder()
                .name(tagDTO.getName())
                .build();
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long id, tagDTO tagDTO) {
        Tag existing = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        existing.setName(tagDTO.getName());
        return tagRepository.save(existing);
    }

    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new RuntimeException("Tag not found");
        }
        tagRepository.deleteById(id);
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
