/**
 * Author: Sudhindra Kumar Saxena
 * User: sudhindra.saxena
 * Date: 02/06/25
 */

package com.sudhindra.service;


import java.util.List;
import com.sudhindra.entities.Comment;

public interface ICommentService {
    List<Comment> fetchAndStoreCommentsFromApi();
    List<Comment> fetchAllComments();
    List<Comment> fetchAllCommentsForPost(Long postId);
}
