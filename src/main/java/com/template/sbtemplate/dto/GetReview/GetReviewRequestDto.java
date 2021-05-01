package com.template.sbtemplate.dto.GetReview;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GetReviewRequestDto {
    private int page;
    private int size;
    private String sortby;
    private String id;
}
