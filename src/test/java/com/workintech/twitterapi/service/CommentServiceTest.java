package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Comment;
import com.workintech.twitterapi.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    private CommentService commentService;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentRepository);
    }

    @Test
    void save_shouldSaveComment() {

        Comment comment = new Comment();
        comment.setContent("Test yorum");

        when(commentRepository.save(comment)).thenReturn(comment);

        Comment result = commentService.save(comment);

        assertNotNull(result);
        assertEquals("Test yorum", result.getContent());

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void findById_shouldReturnComment() {

        Comment comment = new Comment();
        comment.setContent("Bulunan yorum");

        when(commentRepository.findById(1L))
                .thenReturn(Optional.of(comment));

        Optional<Comment> result = commentService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Bulunan yorum", result.get().getContent());

        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void delete_shouldDeleteComment() {

        Comment comment = new Comment();

        commentService.delete(comment);

        verify(commentRepository, times(1)).delete(comment);
    }
}