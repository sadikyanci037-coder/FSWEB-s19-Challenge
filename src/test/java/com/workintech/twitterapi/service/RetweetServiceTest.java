package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Retweet;
import com.workintech.twitterapi.repository.RetweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetweetServiceTest {

    @Mock
    private RetweetRepository retweetRepository;

    private RetweetService retweetService;

    @BeforeEach
    void setUp() {
        retweetService = new RetweetService(retweetRepository);
    }

    @Test
    void save_shouldSaveRetweet() {

        Retweet retweet = new Retweet();

        when(retweetRepository.save(retweet)).thenReturn(retweet);

        Retweet result = retweetService.save(retweet);

        assertNotNull(result);

        verify(retweetRepository, times(1)).save(retweet);
    }

    @Test
    void findById_shouldReturnRetweet() {

        Retweet retweet = new Retweet();

        when(retweetRepository.findById(1L))
                .thenReturn(Optional.of(retweet));

        Optional<Retweet> result =
                retweetService.findById(1L);

        assertTrue(result.isPresent());

        verify(retweetRepository, times(1)).findById(1L);
    }

    @Test
    void findByUserIdAndTweetId_shouldReturnRetweet() {

        Retweet retweet = new Retweet();

        when(retweetRepository.findByUserIdAndTweetId(1L, 2L))
                .thenReturn(Optional.of(retweet));

        Optional<Retweet> result =
                retweetService.findByUserIdAndTweetId(1L, 2L);

        assertTrue(result.isPresent());

        verify(retweetRepository, times(1))
                .findByUserIdAndTweetId(1L, 2L);
    }

    @Test
    void delete_shouldDeleteRetweet() {

        Retweet retweet = new Retweet();

        retweetService.delete(retweet);

        verify(retweetRepository, times(1)).delete(retweet);
    }
}