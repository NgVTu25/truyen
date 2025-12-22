package com.search.truyen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CommentDTO {

    private Long id;

    private Long storyId;

    private Long chapterId;

    private Long userId;

    private String content;
}
