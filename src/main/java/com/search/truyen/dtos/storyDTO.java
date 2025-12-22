package com.search.truyen.dtos;

import java.util.List;

import com.search.truyen.enums.Storytype;
import com.search.truyen.model.entities.Chapter;
import com.search.truyen.model.entities.Tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class storyDTO {

    private long id;

    private String title;

    private String description;

    private List<Chapter> chapters;

    private List<Tag> tags;

    private String coverImage;

    private Storytype type;

}
