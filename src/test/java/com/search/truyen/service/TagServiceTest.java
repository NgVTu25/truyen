package com.search.truyen.service;

import com.search.truyen.dtos.tagDTO;
import com.search.truyen.model.entities.Tag;
import com.search.truyen.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    private Tag testTag;
    private tagDTO testTagDTO;

    @BeforeEach
    void setUp() {
        testTag = Tag.builder()
                .id(1L)
                .name("Action")
                .build();

        testTagDTO = new tagDTO();
        testTagDTO.setId(1L);
        testTagDTO.setName("Action");
    }

    @Test
    void createTag_ShouldCreateTag_WhenValidInput() {
        when(tagRepository.save(any(Tag.class))).thenReturn(testTag);

        Tag result = tagService.createTag(testTagDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Action");
        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    void updateTag_ShouldUpdateTag_WhenTagExists() {
        when(tagRepository.findById(1L)).thenReturn(Optional.of(testTag));
        when(tagRepository.save(any(Tag.class))).thenReturn(testTag);

        testTagDTO.setName("Adventure");
        Tag result = tagService.updateTag(1L, testTagDTO);

        assertThat(result).isNotNull();
        verify(tagRepository, times(1)).findById(1L);
        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    void updateTag_ShouldThrowException_WhenTagNotFound() {
        when(tagRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tagService.updateTag(999L, testTagDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Tag not found");
    }

    @Test
    void deleteTag_ShouldDeleteTag_WhenTagExists() {
        when(tagRepository.existsById(1L)).thenReturn(true);
        doNothing().when(tagRepository).deleteById(1L);

        tagService.deleteTag(1L);

        verify(tagRepository, times(1)).existsById(1L);
        verify(tagRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTag_ShouldThrowException_WhenTagNotFound() {
        when(tagRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> tagService.deleteTag(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Tag not found");
    }

    @Test
    void getTagById_ShouldReturnTag_WhenTagExists() {
        when(tagRepository.findById(1L)).thenReturn(Optional.of(testTag));

        Tag result = tagService.getTagById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Action");
    }

    @Test
    void getTagById_ShouldReturnNull_WhenTagNotFound() {
        when(tagRepository.findById(999L)).thenReturn(Optional.empty());

        Tag result = tagService.getTagById(999L);

        assertThat(result).isNull();
    }

    @Test
    void getAllTags_ShouldReturnListOfTags() {
        Tag tag2 = Tag.builder().id(2L).name("Adventure").build();
        List<Tag> tags = Arrays.asList(testTag, tag2);
        when(tagRepository.findAll()).thenReturn(tags);

        List<Tag> result = tagService.getAllTags();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Action");
        assertThat(result.get(1).getName()).isEqualTo("Adventure");
    }
}
