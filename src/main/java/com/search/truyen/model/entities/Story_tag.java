package com.search.truyen.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "story_tag")
public class Story_tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_tag_id")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "story_id")
    private List<Story> stories;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tag_id")
    private List<Tag> tags;
}
