package com.template.sbtemplate.dto.AddReview;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddReviewRequestDto {
  private String reviewerName;

  private String title;

  private int rating;

  private String comment;
}
