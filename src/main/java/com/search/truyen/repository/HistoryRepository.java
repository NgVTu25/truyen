package com.search.truyen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.search.truyen.model.entities.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    // Tìm tất cả history theo user_id
    List<History> findByUserId(Long userId);

    // Tìm tất cả history theo story_id
    List<History> findByStoryId(Long storyId);

    // Tìm history theo user_id và story_id
    Optional<History> findByUserIdAndStoryId(Long userId, Long storyId);

    // Tìm history theo user_id, sắp xếp theo thời gian đọc gần nhất
    List<History> findByUserIdOrderByLastReadDesc(Long userId);
}
