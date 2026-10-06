package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.repository.TweetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;

    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public Tweet save(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    public List<Tweet> findByUserId(Long userId) {
        return tweetRepository.findByUserId(userId);
    }

    public Optional<Tweet> findById(Long id) {
        return tweetRepository.findById(id);
    }

    public void delete(Tweet tweet) {
        tweetRepository.delete(tweet);
    }
}