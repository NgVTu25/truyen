package com.search.truyen.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

import com.search.truyen.dtos.TaglogDTO;
import com.search.truyen.model.entities.Tag_log;
import com.search.truyen.repository.tag_logRepository;

@Service
@RequiredArgsConstructor
public class Tag_logService {
    private final tag_logRepository tag_logRepository;

    public Tag_log createTag_log(TaglogDTO tag_log) {
        Tag_log tag_logEntity = new Tag_log();
        tag_logEntity.setStory(tag_log.getStory());
        tag_logEntity.setSummary(tag_log.getSummary());
        tag_logEntity.setGenerated_tag(tag_log.getGenerated_tag());
        return tag_logRepository.save(tag_logEntity);
    }

    public Tag_log updateTag_log(TaglogDTO tag_log) {
        Tag_log tag_logEntity = tag_logRepository.findById(tag_log.getId())
                .orElseThrow(() -> new RuntimeException("Tag_log not found with id: " + tag_log.getId()));

        tag_logEntity.setStory(tag_log.getStory());
        tag_logEntity.setSummary(tag_log.getSummary());
        tag_logEntity.setGenerated_tag(tag_log.getGenerated_tag());
        return tag_logRepository.save(tag_logEntity);
    }

    public void deleteTag_log(TaglogDTO tag_log) {
        if (!tag_logRepository.existsById(tag_log.getId())) {
            throw new RuntimeException("Tag_log not found with id: " + tag_log.getId());
        }
        tag_logRepository.deleteById(tag_log.getId());
    }

    public Tag_log getTag_logById(TaglogDTO tag_log) {
        return tag_logRepository.findById(tag_log.getId()).orElse(null);
    }

    public List<Tag_log> getAllTag_logs() {
        return tag_logRepository.findAll();
    }
}
