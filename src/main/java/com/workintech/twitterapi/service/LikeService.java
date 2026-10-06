package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Like;
import com.workintech.twitterapi.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like save(Like like) {
        return likeRepository.save(like);
    }

    public Optional<Like> findByUserIdAndTweetId(Long userId, Long tweetId) {
        return likeRepository.findByUserIdAndTweetId(userId, tweetId);
    }

    public void delete(Like like) {
        likeRepository.delete(like);
    }
}