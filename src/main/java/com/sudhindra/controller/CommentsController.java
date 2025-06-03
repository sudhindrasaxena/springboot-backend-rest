/**
 * Author: Sudhindra Kumar Saxena
 * User: sudhindra.saxena
 * Date: 02/06/25
 */

package com.sudhindra.controller;

import com.sudhindra.dto.CommentsRequest;
import com.sudhindra.dto.CommentsResponse;
import com.sudhindra.entities.Comment;
import com.sudhindra.service.impl.CommentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comments;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/comments")
public class CommentsController {
    private final CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(path = "/getAllComments")
    public CommentsResponse getAllComments() {
        try {
            log.info("Fetching all comments from database");
            return CommentsResponse.builder()
                    .commentList(commentService.fetchAllComments())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/getCommentsForPost/{postId}")
    public CommentsResponse getAllComments(@PathVariable Long postId) {
        try {
            log.info("Fetching all comments for Post ID : {}", postId);
            return CommentsResponse.builder()
                    .commentList(commentService.fetchAllCommentsForPost(postId))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping(path = "/deleteComment/{id}")
    public CommentsResponse deleteComment(@PathVariable Integer id) {
        try {
            log.info("Deleting comment for with ID : {}", id);
            Comment comment = commentService.deleteCommentForId(id);
            return CommentsResponse.builder()
                    .message("Deleted Comment With ID : " + comment.getId())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "sync")
    public CommentsResponse refreshCommentsFromAPI() {
        try {
            log.info("Fetching all comments From API to persist");
            return CommentsResponse.builder()
                    .commentList(commentService.fetchAndStoreCommentsFromApi())
                    .message("Successfully Fetched Response !")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "addComment", consumes = "application/json")
    public CommentsResponse addComment(@Valid @RequestBody CommentsRequest request) {
        log.info("Adding comment to DB : {}", request.toString());
        Comment comment = commentService.addComment(request);

        return CommentsResponse.builder()
                .message("Added Comment : " + comment.toString())
                .build();
    }

    @PutMapping(path = "updateComment")
    public CommentsResponse updateComment(@Valid @RequestBody CommentsRequest request) {
        Comment comment = commentService.updateComment(request);
        return CommentsResponse.builder()
                .message("Updated comment with details : " + comment.toString())
                .build();
    }
}