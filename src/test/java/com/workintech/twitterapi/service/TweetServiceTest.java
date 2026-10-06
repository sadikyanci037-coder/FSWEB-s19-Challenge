package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.repository.TweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    private TweetService tweetService;

    @BeforeEach
    void setUp() {
        tweetService = new TweetService(tweetRepository);
    }

    @Test
    void save_shouldSaveTweet() {

        Tweet tweet = new Tweet();
        tweet.setContent("Test tweet");

        when(tweetRepository.save(tweet)).thenReturn(tweet);

        Tweet result = tweetService.save(tweet);

        assertNotNull(result);
        assertEquals("Test tweet", result.getContent());

        verify(tweetRepository, times(1)).save(tweet);
    }

    @Test
    void findById_shouldReturnTweet() {

        Tweet tweet = new Tweet();
        tweet.setContent("Bulunan tweet");

        when(tweetRepository.findById(1L))
                .thenReturn(Optional.of(tweet));

        Optional<Tweet> result = tweetService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Bulunan tweet", result.get().getContent());

        verify(tweetRepository, times(1)).findById(1L);
    }

    @Test
    void findByUserId_shouldReturnTweetList() {

        Tweet tweet1 = new Tweet();
        tweet1.setContent("Tweet 1");

        Tweet tweet2 = new Tweet();
        tweet2.setContent("Tweet 2");

        when(tweetRepository.findByUserId(1L))
                .thenReturn(List.of(tweet1, tweet2));

        List<Tweet> result = tweetService.findByUserId(1L);

        assertEquals(2, result.size());
        assertEquals("Tweet 1", result.get(0).getContent());
        assertEquals("Tweet 2", result.get(1).getContent());

        verify(tweetRepository, times(1)).findByUserId(1L);
    }

    @Test
    void delete_shouldDeleteTweet() {

        Tweet tweet = new Tweet();

        tweetService.delete(tweet);

        verify(tweetRepository, times(1)).delete(tweet);
    }
}