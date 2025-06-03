/**
 * Author: Sudhindra Kumar Saxena
 * User: sudhindra.saxena
 * Date: 02/06/25
 */

package com.sudhindra;

import com.real.interview.entities.Comment;
import com.real.interview.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void testSaveAndFindById() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setPostId(101);
        comment.setName("Test Name");
        comment.setEmail("test@example.com");
        comment.setBody("This is a test body");

        commentRepository.save(comment);

        Optional<Comment> found = commentRepository.findById(1);
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test Name");
    }

}
