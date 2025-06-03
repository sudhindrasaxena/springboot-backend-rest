/**
 * Author: Sudhindra Kumar Saxena
 * User: sudhindra.saxena
 * Date: 02/06/25
 */

package com.sudhindra.service.impl;

import com.sudhindra.dto.CommentsRequest;
import com.sudhindra.dto.CommentsResponse;
import com.sudhindra.entities.Comment;
import com.sudhindra.repository.CommentRepository;
import com.sudhindra.service.ICommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final RestTemplate restTemplate;

    public CommentService(CommentRepository commentRepository, RestTemplate restTemplate) {
        this.commentRepository = commentRepository;
        this.restTemplate = restTemplate;
    }

    @Value("${comments.api}")
    private String commentAPI;

    @Override
    public List<Comment> fetchAndStoreCommentsFromApi() {
        List<Comment> commentList = restTemplate.exchange(commentAPI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Comment>>() {
                }).getBody();

        return commentRepository.saveAll(commentList);
    }

    @Override
    public List<Comment> fetchAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> fetchAllCommentsForPost(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Override
    public Comment deleteCommentForId(Integer id) {
        commentRepository.deleteById(id);
        return Comment.builder()
                .id(id)
                .build();
    }

    @Override
    public Comment addComment(CommentsRequest request) {
        Comment comment = Comment.builder()
                .id(request.getId())
                .postId(request.getPostId())
                .name(request.getName())
                .email(request.getEmail())
                .body(request.getBody())
                .build();

        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(CommentsRequest request) {
        Comment comment = Comment.builder()
                .id(request.getId())
                .postId(request.getPostId())
                .name(request.getName())
                .email(request.getEmail())
                .body(request.getBody())
                .build();

        return commentRepository.save(comment);
    }
}
