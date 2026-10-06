package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Like;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.exceptions.ApiException;
import com.workintech.twitterapi.service.LikeService;
import com.workintech.twitterapi.service.TweetService;
import com.workintech.twitterapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;
    private final TweetService tweetService;

    public LikeController(
            LikeService likeService,
            UserService userService,
            TweetService tweetService) {

        this.likeService = likeService;
        this.userService = userService;
        this.tweetService = tweetService;
    }

    @PostMapping("/like")
    public ResponseEntity<Like> likeTweet(
            Principal principal,
            @RequestParam Long tweetId) {

        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() ->
                        new ApiException(
                                "Kullanıcı bulunamadı.",
                                HttpStatus.NOT_FOUND
                        )
                );

        Tweet tweet = tweetService.findById(tweetId)
                .orElseThrow(() ->
                        new ApiException(
                                "Tweet bulunamadı.",
                                HttpStatus.NOT_FOUND
                        )
                );

        if (likeService
                .findByUserIdAndTweetId(user.getId(), tweetId)
                .isPresent()) {

            throw new ApiException(
                    "Bu tweet zaten beğenilmiş.",
                    HttpStatus.BAD_REQUEST
            );
        }

        Like like = new Like(user, tweet);

        return ResponseEntity.ok(likeService.save(like));
    }

    @PostMapping("/dislike")
    public ResponseEntity<String> dislikeTweet(
            Principal principal,
            @RequestParam Long tweetId) {

        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() ->
                        new ApiException(
                                "Kullanıcı bulunamadı.",
                                HttpStatus.NOT_FOUND
                        )
                );

        Like like = likeService
                .findByUserIdAndTweetId(user.getId(), tweetId)
                .orElseThrow(() ->
                        new ApiException(
                                "Bu tweet beğenilmemiş.",
                                HttpStatus.NOT_FOUND
                        )
                );

        likeService.delete(like);

        return ResponseEntity.ok("Like kaldırıldı.");
    }
}