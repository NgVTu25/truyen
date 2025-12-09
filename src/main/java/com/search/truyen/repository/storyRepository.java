package com.search.truyen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.search.truyen.enums.Storytype;
import com.search.truyen.model.entities.Story;

@Repository
public interface storyRepository extends JpaRepository<Story, Long> {

    Optional<Story> findByTitle(String title);

    List<Story> findByType(Storytype type);

    Optional<Story> findFirstByTitleContainingIgnoreCase(String title);
}
