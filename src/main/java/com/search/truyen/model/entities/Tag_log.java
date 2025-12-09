package com.search.truyen.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tag_log")
public class Tag_log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_log_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Story_id")
    private Story story;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "generated_tag", columnDefinition = "TEXT")
    private String generated_tag;

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime createAt;

}
