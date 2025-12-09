package com.search.truyen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.search.truyen.model.entities.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
