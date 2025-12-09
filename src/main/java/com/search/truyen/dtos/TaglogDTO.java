package com.search.truyen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.search.truyen.model.entities.Story;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TaglogDTO {

    private long id;

    private Story story;

    private String summary;

    private String generated_tag;
}
