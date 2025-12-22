package com.search.truyen.model.entities;

import com.search.truyen.enums.Storytype;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "story")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapter> chapters;
    @Column(name = "view_count")
    private Long viewCount = 0L;

    @Column(name = "status")
    private String status; // ONGOING, COMPLETED

    @Column(name = "slug", unique = true)
    private String slug;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "story_tags",
            joinColumns = @JoinColumn(name = "story_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;



    @Column(name = "cover_image")
    private String coverImage;

    @Enumerated(EnumType.STRING)
    private Storytype type;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }
}