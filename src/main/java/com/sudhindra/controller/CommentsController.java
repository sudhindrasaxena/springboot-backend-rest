/**
 * Author: Sudhindra Kumar Saxena
 * User: sudhindra.saxena
 * Date: 02/06/25
 */

package com.sudhindra.controller;

import com.sudhindra.dto.CommentsResponse;
import com.sudhindra.service.impl.CommentService;
import lombok.extern.slf4j.Slf4j;
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

    @DeleteMapping(path="/deleteComment")
    public CommentsResponse deleteComment(@PathVariable Long Id){
        return null;
    }

    @PostMapping("sync")
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
}