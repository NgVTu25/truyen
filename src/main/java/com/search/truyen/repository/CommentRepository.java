package com.search.truyen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.search.truyen.model.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Tìm tất cả comment theo story_id
    List<Comment> findByStoryId(Long storyId);

    // Tìm tất cả comment theo chapter_id
    List<Comment> findByChapterId(Long chapterId);

    // Tìm tất cả comment theo user_id
    List<Comment> findByUserId(Long userId);

    // Tìm comment theo story_id, sắp xếp theo thời gian tạo giảm dần
    List<Comment> findByStoryIdOrderByCreateAtDesc(Long storyId);
}
