package com.search.truyen.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.search.truyen.dtos.TaglogDTO;
import com.search.truyen.model.entities.Tag_log;
import com.search.truyen.repository.tag_logRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Tag_logService {
    private final tag_logRepository tag_logRepository;
    private final ModelMapper modelMapper;

    public TaglogDTO createTag_log(TaglogDTO taglogDTO) {
        Tag_log entity = modelMapper.map(taglogDTO, Tag_log.class);
        return modelMapper.map(tag_logRepository.save(entity), TaglogDTO.class);
    }

    public TaglogDTO updateTag_log(Long id, TaglogDTO taglogDTO) {
        Tag_log entity = tag_logRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag_log not found"));
        modelMapper.map(taglogDTO, entity);
        entity.setId(id);
        return modelMapper.map(tag_logRepository.save(entity), TaglogDTO.class);
    }

    public void deleteTag_log(Long id) {
        if (!tag_logRepository.existsById(id)) throw new RuntimeException("Tag_log not found");
        tag_logRepository.deleteById(id);
    }

    public Optional<TaglogDTO> getTag_logById(Long id) {
        return tag_logRepository.findById(id)
                .map(t -> modelMapper.map(t, TaglogDTO.class));
    }

    public List<TaglogDTO> getAllTag_logs() {
        return tag_logRepository.findAll().stream()
                .map(t -> modelMapper.map(t, TaglogDTO.class))
                .collect(Collectors.toList());
    }
}