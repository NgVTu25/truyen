package com.search.truyen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.search.truyen.model.entities.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    List<Chapter> findByStoryId(Long storyId);

    Optional<Chapter> findByStoryIdAndChapterNumber(Long storyId, int chapterNumber);

    List<Chapter> findByTitleContainingIgnoreCase(String title);
}
