package com.adam.commoninterestsservice.services;

import com.adam.commoninterestsservice.entities.Comment;
import com.adam.commoninterestsservice.entities.User;
import com.adam.commoninterestsservice.exceptions.PostNotFoundException;
import com.adam.commoninterestsservice.repositories.CommentRepository;
import com.adam.commoninterestsservice.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public Collection<Comment> getAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    public Comment addComment(Comment input, Long postId, User author) {
        Comment toSave = new Comment();
        toSave.setAuthor(author);
        toSave.setContents(input.getContents());
        toSave.setPost(postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found.")));
        return commentRepository.save(toSave);
    }

    public void removeComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
