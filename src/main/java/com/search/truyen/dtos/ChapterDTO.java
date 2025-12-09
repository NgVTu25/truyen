package com.search.truyen.dtos;

import java.util.List;

import com.search.truyen.model.entities.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterDTO {

    private long id;

    private String title;

    private int chapterNumber;

    private Long storyId;

    private List<Page> pages;

    private String content;
}
