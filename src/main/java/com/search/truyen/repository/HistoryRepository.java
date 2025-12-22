package com.search.truyen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.search.truyen.model.entities.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByUserId(Long userId);

    List<History> findByStoryId(Long storyId);

    Optional<History> findByUserIdAndStoryId(Long userId, Long storyId);

    List<History> findByUserIdOrderByLastReadDesc(Long userId);
}
