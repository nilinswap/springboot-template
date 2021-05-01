package com.template.sbtemplate.service;

import com.template.sbtemplate.dto.AddReview.AddReviewRequestDto;
import com.template.sbtemplate.dto.AddReview.AddReviewResponseDto;
import com.template.sbtemplate.dto.GetReview.GetReviewRequestDto;
import com.template.sbtemplate.dto.GetReview.GetReviewResponseDto;

public interface IRatingReviewService {
    GetReviewResponseDto getReview(GetReviewRequestDto getReviewRequestDto);
    AddReviewResponseDto addReview(AddReviewRequestDto getReviewRequestDto);
}
