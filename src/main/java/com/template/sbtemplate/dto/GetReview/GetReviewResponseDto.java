package com.template.sbtemplate.dto.GetReview;

import com.template.sbtemplate.pojo.GetReview.Response.ReviewResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetReviewResponseDto {
    private List<ReviewResponse> reviews;
}
