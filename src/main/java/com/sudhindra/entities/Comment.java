/**
 * Author: Sudhindra Kumar Saxena
 * User: sudhindra.saxena
 * Date: 02/06/25
 */

package com.sudhindra.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    private int id;
    private int postId;

    private String name;
    private String email;

    @Column(length = 20000)
    private String body;
}
