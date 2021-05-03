package com.template.sbtemplate.service.impl;

import com.template.sbtemplate.dto.AddReview.AddReviewRequestDto;
import com.template.sbtemplate.dto.AddReview.AddReviewResponseDto;
import com.template.sbtemplate.dto.GetRatings.GetRatingsResponseDto;
import com.template.sbtemplate.dto.GetReview.GetReviewRequestDto;
import com.template.sbtemplate.dto.GetReview.GetReviewResponseDto;
import com.template.sbtemplate.pojo.GetReview.Response.ReviewResponse;
import com.template.sbtemplate.model.Review;
import com.template.sbtemplate.repositories.ReviewRepository;
import com.template.sbtemplate.service.IRatingReviewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class RatingReviewService implements IRatingReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    protected List<ReviewResponse> getReviewsConditionally(GetReviewRequestDto getReviewRequestDto){
        List<Review> reviewList = Collections.<Review>emptyList();
        String review_id = getReviewRequestDto.getId();
        String sortby =  getReviewRequestDto.getSortby();
        int page =  getReviewRequestDto.getPage();
        int size =  getReviewRequestDto.getSize();
        Pageable pageable = PageRequest.of(page, size);
        if( review_id != null){
            reviewList.add(reviewRepository.findFirstById(review_id));
        }else if(sortby != null){
            if(sortby.equals("time"))
                reviewList = reviewRepository.findAllByOrderByCreatedOnDesc(pageable);
            else if(sortby.equals("rating"))
                reviewList = reviewRepository.findAllByOrderByRatingDesc(pageable);
            else{
                reviewList = reviewRepository.findAllByOrderByCreatedOnDesc(pageable);
            }
        }
        else{
            reviewList = reviewRepository.findAllByOrderByCreatedOnDesc(pageable);
        }
        log.debug(String.format("reviews: %s", reviewList.toString()));
        List<ReviewResponse> reviews = reviewList.stream().map(
                review -> ReviewResponse.builder()
                            .reviewerName(review.getReviewerName())
                            .rating(review.getRating())
                            .title(review.getTitle())
                            .comment(review.getComment())
                            .createdOn(review.getCreatedOn())
                            .build()
        ).collect(Collectors.toList());
        return reviews;
    }
    public GetReviewResponseDto getReview(GetReviewRequestDto getReviewRequestDto){
        List<ReviewResponse> reviews = getReviewsConditionally(getReviewRequestDto);
        return GetReviewResponseDto.builder().reviews(reviews).build();
    }
    public AddReviewResponseDto addReview(AddReviewRequestDto addReviewRequestDto){
        Review review = Review.builder()
                .reviewerName(addReviewRequestDto.getReviewerName())
                .rating(addReviewRequestDto.getRating())
                .title(addReviewRequestDto.getTitle())
                .comment(addReviewRequestDto.getComment())
                .build();
        reviewRepository.save(review);
        return AddReviewResponseDto.builder().build();
    }
    public GetRatingsResponseDto getRatings(){
        Tuple tuple = reviewRepository.findCountAverageRating();
        return GetRatingsResponseDto.builder().count(tuple.get(0, BigInteger.class).longValue()).average(tuple.get(1, BigDecimal.class).floatValue()).build();
    }

}
