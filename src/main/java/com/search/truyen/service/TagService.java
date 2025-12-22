package com.search.truyen.service;

import java.util.List;
import java.util.stream.Collectors;
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

    public tagDTO createTag(tagDTO tagDTO) {
        Tag tag = modelMapper.map(tagDTO, Tag.class);
        return modelMapper.map(tagRepository.save(tag), tagDTO.class);
    }

    public tagDTO updateTag(Long id, tagDTO tagDTO) {
        Tag existing = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        modelMapper.map(tagDTO, existing);
        existing.setId(id);
        return modelMapper.map(tagRepository.save(existing), tagDTO.class);
    }

    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) throw new RuntimeException("Tag not found");
        tagRepository.deleteById(id);
    }

    public tagDTO getTagById(Long id) {
        return tagRepository.findById(id)
                .map(t -> modelMapper.map(t, tagDTO.class))
                .orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public List<tagDTO> getAllTags() {
        return tagRepository.findAll().stream()
                .map(t -> modelMapper.map(t, tagDTO.class))
                .collect(Collectors.toList());
    }
}