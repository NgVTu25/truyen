package com.search.truyen.dtos;

import java.util.List;

import com.search.truyen.model.entities.Page;

import lombok.Data;

@Data
public class ChapterDTO {

    private long id;

    private String title;

    private int chapterNumber;

    private Long storyId;

    private List<Page> pages;

    private String content;
}
