/**
 * Author: Sudhindra Kumar Saxena
 * User: sudhindra.saxena
 * Date: 02/06/25
 */

package com.sudhindra;

import com.sudhindra.dto.CommentsRequest;
import com.sudhindra.entities.Comment;
import com.sudhindra.repository.CommentRepository;
import com.sudhindra.service.impl.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        // Manually set the @Value-injected field
        ReflectionTestUtils.setField(commentService, "commentAPI", "http://fake.api/comments");
    }

    @Test
    void testFetchAndStoreCommentsFromApi() {
        // Given
        List<Comment> mockComments = List.of(new Comment(1, 1, "name", "email@test.com", "body"));
        ResponseEntity<List<Comment>> mockResponse = ResponseEntity.ok(mockComments);

        // Correctly mock the call with generic type
        when(restTemplate.exchange(
                eq("http://fake.api/comments"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class))
        ).thenReturn(mockResponse);

        when(commentRepository.saveAll(anyList())).thenReturn(mockComments);

        // When
        List<Comment> result = commentService.fetchAndStoreCommentsFromApi();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(commentRepository, times(1)).saveAll(mockComments);
    }

    @Test
    void testFetchAllComments() {
        List<Comment> mockList = List.of(new Comment(1, 1, "A", "a@email.com", "Body"));
        Mockito.when(commentRepository.findAll()).thenReturn(mockList);

        List<Comment> result = commentService.fetchAllComments();

        Assertions.assertEquals(1, result.size());
    }

    @Test
    void testFetchAllCommentsForPost() {
        List<Comment> mockList = List.of(new Comment(1, 1, "A", "a@email.com", "Body"));
        Mockito.when(commentRepository.findAllByPostId(1L)).thenReturn(mockList);

        List<Comment> result = commentService.fetchAllCommentsForPost(1L);

        Assertions.assertEquals(1, result.size());
    }

    @Test
    void testDeleteCommentForId() {
        Integer id = 5;
        Comment result = commentService.deleteCommentForId(id);

        Mockito.verify(commentRepository, Mockito.times(1)).deleteById(id);
        Assertions.assertEquals(id, result.getId());
    }

    @Test
    void testAddComment() {
        CommentsRequest request = new CommentsRequest(5, 1, "Name", "email@test.com", "Some text");
        Comment expected = Comment.builder().id(5).postId(1).name("Name").email("email@test.com").body("Some text").build();
        Mockito.when(commentRepository.save(any(Comment.class))).thenReturn(expected);
        Comment result = commentService.addComment(request);

        Assertions.assertEquals(expected.getId(), result.getId());
    }

    @Test
    void testUpdateComment() {
        CommentsRequest request = new CommentsRequest(10, 2, "Updated", "update@test.com", "Updated body");
        Comment expected = Comment.builder().id(10).postId(2).name("Updated").email("update@test.com").body("Updated body").build();
        Mockito.when(commentRepository.save(any(Comment.class))).thenReturn(expected);
        Comment result = commentService.updateComment(request);

        Assertions.assertEquals("Updated", result.getName());
    }
}
