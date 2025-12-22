package com.search.truyen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.search.truyen.model.entities.Story;
import com.search.truyen.model.entities.Tag_log;

@Repository
public interface tag_logRepository extends JpaRepository<Tag_log, Long> {
}
