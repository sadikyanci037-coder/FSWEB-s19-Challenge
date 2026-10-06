package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Like;
import com.workintech.twitterapi.repository.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    private LikeService likeService;

    @BeforeEach
    void setUp() {
        likeService = new LikeService(likeRepository);
    }

    @Test
    void save_shouldSaveLike() {

        Like like = new Like();

        when(likeRepository.save(like)).thenReturn(like);

        Like result = likeService.save(like);

        assertNotNull(result);

        verify(likeRepository, times(1)).save(like);
    }

    @Test
    void findByUserIdAndTweetId_shouldReturnLike() {

        Like like = new Like();

        when(likeRepository.findByUserIdAndTweetId(1L, 2L))
                .thenReturn(Optional.of(like));

        Optional<Like> result =
                likeService.findByUserIdAndTweetId(1L, 2L);

        assertTrue(result.isPresent());

        verify(likeRepository, times(1))
                .findByUserIdAndTweetId(1L, 2L);
    }

    @Test
    void delete_shouldDeleteLike() {

        Like like = new Like();

        likeService.delete(like);

        verify(likeRepository, times(1)).delete(like);
    }
}