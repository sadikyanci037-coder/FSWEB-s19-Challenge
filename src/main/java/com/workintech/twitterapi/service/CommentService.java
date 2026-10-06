package com.workintech.twitterapi.service;

import com.workintech.twitterapi.entity.Comment;
import com.workintech.twitterapi.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}