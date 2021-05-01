package com.template.sbtemplate.pojo.GetReview.Response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReviewResponse {
    private String reviewerName;

    private int rating;

    private String title;

    private String comment;

    private LocalDateTime createdOn;
}
