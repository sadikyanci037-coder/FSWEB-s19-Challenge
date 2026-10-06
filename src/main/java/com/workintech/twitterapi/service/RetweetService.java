package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Retweet;
import com.workintech.twitterapi.repository.RetweetRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RetweetService {

    private final RetweetRepository retweetRepository;

    public RetweetService(RetweetRepository retweetRepository) {
        this.retweetRepository = retweetRepository;
    }

    public Retweet save(Retweet retweet) {
        return retweetRepository.save(retweet);
    }

    public Optional<Retweet> findById(Long id) {
        return retweetRepository.findById(id);
    }

    public Optional<Retweet> findByUserIdAndTweetId(Long userId, Long tweetId) {
        return retweetRepository.findByUserIdAndTweetId(userId, tweetId);
    }

    public void delete(Retweet retweet) {
        retweetRepository.delete(retweet);
    }
}