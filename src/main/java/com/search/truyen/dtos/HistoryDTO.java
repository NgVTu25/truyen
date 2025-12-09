package com.search.truyen.dtos;

import lombok.Data;

@Data
public class HistoryDTO {

    private Long id;

    private Long userId;

    private Long storyId;

    private Long chapterId;

    private int lastPage;
}
