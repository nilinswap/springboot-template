package com.template.sbtemplate.model;

import com.template.sbtemplate.constant.Constants;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = Constants.Database.REVIEW_TABLE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reviewer_name")
    public String reviewerName;

    @Column(name = "user_id")
    public String userId;

    @Column(name = "rating")
    public int rating;

    @Column(name = "title")
    public String title;

    @Column(name = "comment")
    public String comment;

    @Column(name = "is_approved")
    public boolean isApproved;


    @Column(name = "created_on")
    @CreationTimestamp
    private LocalDateTime createdOn;


    @Column(name = "updated_on")
    @UpdateTimestamp
    private LocalDateTime updatedOn;
}
