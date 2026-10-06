package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.exceptions.ApiException;
import com.workintech.twitterapi.service.TweetService;
import com.workintech.twitterapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService tweetService;
    private final UserService userService;

    public TweetController(TweetService tweetService, UserService userService) {
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Tweet> createTweet(
            Principal principal,
            @Valid @RequestBody Tweet tweet) {

        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() ->
                        new ApiException(
                                "Kullanıcı bulunamadı.",
                                HttpStatus.NOT_FOUND
                        )
                );

        tweet.setUser(user);

        return ResponseEntity.ok(tweetService.save(tweet));
    }

    @GetMapping("/findByUserId")
    public ResponseEntity<List<Tweet>> findByUserId(
            @RequestParam Long userId) {

        userService.findById(userId)
                .orElseThrow(() ->
                        new ApiException(
                                "Kullanıcı bulunamadı.",
                                HttpStatus.NOT_FOUND
                        )
                );

        return ResponseEntity.ok(tweetService.findByUserId(userId));
    }

    @GetMapping("/findById")
    public ResponseEntity<Tweet> findById(
            @RequestParam Long id) {

        Tweet tweet = tweetService.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Tweet bulunamadı.",
                                HttpStatus.NOT_FOUND
                        )
                );

        return ResponseEntity.ok(tweet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tweet> updateTweet(
            @PathVariable Long id,
            Principal principal,
            @Valid @RequestBody Tweet updatedTweet) {

        Tweet tweet = tweetService.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Tweet bulunamadı.",
                                HttpStatus.NOT_FOUND
                        )
                );

        if (!tweet.getUser().getUsername().equals(principal.getName())) {
            throw new ApiException(
                    "Bu tweeti sadece sahibi güncelleyebilir.",
                    HttpStatus.FORBIDDEN
            );
        }

        tweet.setContent(updatedTweet.getContent());

        return ResponseEntity.ok(tweetService.save(tweet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTweet(
            @PathVariable Long id,
            Principal principal) {

        Tweet tweet = tweetService.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Tweet bulunamadı.",
                                HttpStatus.NOT_FOUND
                        )
                );

        if (!tweet.getUser().getUsername().equals(principal.getName())) {
            throw new ApiException(
                    "Bu tweeti sadece sahibi silebilir.",
                    HttpStatus.FORBIDDEN
            );
        }

        tweetService.delete(tweet);

        return ResponseEntity.ok("Tweet silindi.");
    }
}