package com.search.truyen.dtos;

import lombok.Data;

@Data
public class CommentDTO {

    private Long id;

    private Long storyId;

    private Long chapterId;

    private Long userId;

    private String content;
}
