package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Retweet;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.exceptions.ApiException;
import com.workintech.twitterapi.service.RetweetService;
import com.workintech.twitterapi.service.TweetService;
import com.workintech.twitterapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/retweet")
public class RetweetController {

    private final RetweetService retweetService;
    private final UserService userService;
    private final TweetService tweetService;

    public RetweetController(
            RetweetService retweetService,
            UserService userService,
            TweetService tweetService) {

        this.retweetService = retweetService;
        this.userService = userService;
        this.tweetService = tweetService;
    }

    @PostMapping
    public ResponseEntity<Retweet> createRetweet(
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

        if (retweetService
                .findByUserIdAndTweetId(user.getId(), tweetId)
                .isPresent()) {

            throw new ApiException(
                    "Bu tweet zaten retweet edilmiş.",
                    HttpStatus.BAD_REQUEST
            );
        }

        Retweet retweet = new Retweet(user, tweet);

        return ResponseEntity.ok(retweetService.save(retweet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRetweet(
            @PathVariable Long id,
            Principal principal) {

        Retweet retweet = retweetService.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Retweet bulunamadı.",
                                HttpStatus.NOT_FOUND
                        )
                );

        if (!retweet.getUser().getUsername().equals(principal.getName())) {
            throw new ApiException(
                    "Bu retweeti sadece sahibi silebilir.",
                    HttpStatus.FORBIDDEN
            );
        }

        retweetService.delete(retweet);

        return ResponseEntity.ok("Retweet silindi.");
    }
}