/**
 * Author: Sudhindra Kumar Saxena
 * User: sudhindra.saxena
 * Date: 02/06/25
 */

package com.sudhindra;

import com.real.interview.entities.Comment;
import com.real.interview.repository.CommentRepository;
import com.real.interview.service.impl.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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
}
