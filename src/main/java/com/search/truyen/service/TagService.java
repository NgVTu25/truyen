package com.search.truyen.service;

import java.util.List;

import com.search.truyen.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.search.truyen.dtos.tagDTO;
import com.search.truyen.model.entities.Tag;
import com.search.truyen.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public Tag createTag(tagDTO tagDTO) {
        Tag tag = new Tag();
        modelMapper.map(tagDTO, tag);
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long id, tagDTO tagDTO) {
        Tag existing = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        modelMapper.map(tagDTO, existing);
        return tagRepository.save(existing);
    }

    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new RuntimeException("Tag not found");
        }
        tagRepository.deleteById(id);
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
}
