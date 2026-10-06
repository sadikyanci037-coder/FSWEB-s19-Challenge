package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.Comment;
import com.workintech.twitterapi.entity.Tweet;
import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.exceptions.ApiException;
import com.workintech.twitterapi.service.CommentService;
import com.workintech.twitterapi.service.TweetService;
import com.workintech.twitterapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final TweetService tweetService;
    private final UserService userService;

    public CommentController(
            CommentService commentService,
            TweetService tweetService,
            UserService userService) {

        this.commentService = commentService;
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(
            Principal principal,
            @RequestParam Long tweetId,
            @Valid @RequestBody Comment comment) {

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

        comment.setUser(user);
        comment.setTweet(tweet);

        return ResponseEntity.ok(commentService.save(comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long id,
            Principal principal,
            @Valid @RequestBody Comment updatedComment) {

        Comment comment = commentService.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Yorum bulunamadı.",
                                HttpStatus.NOT_FOUND
                        )
                );

        if (!comment.getUser().getUsername().equals(principal.getName())) {
            throw new ApiException(
                    "Bu yorumu sadece yorum sahibi güncelleyebilir.",
                    HttpStatus.FORBIDDEN
            );
        }

        comment.setContent(updatedComment.getContent());

        return ResponseEntity.ok(commentService.save(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long id,
            Principal principal) {

        Comment comment = commentService.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Yorum bulunamadı.",
                                HttpStatus.NOT_FOUND
                        )
                );

        String username = principal.getName();

        String commentOwnerUsername = comment.getUser().getUsername();
        String tweetOwnerUsername = comment.getTweet().getUser().getUsername();

        if (!commentOwnerUsername.equals(username)
                && !tweetOwnerUsername.equals(username)) {

            throw new ApiException(
                    "Bu yorumu sadece yorum sahibi veya tweet sahibi silebilir.",
                    HttpStatus.FORBIDDEN
            );
        }

        commentService.delete(comment);

        return ResponseEntity.ok("Yorum silindi.");
    }
}