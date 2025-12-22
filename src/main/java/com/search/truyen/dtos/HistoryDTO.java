package com.search.truyen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class HistoryDTO {

    private Long id;

    private Long userId;

    private Long storyId;

    private Long chapterId;

    private int lastPage;
}
