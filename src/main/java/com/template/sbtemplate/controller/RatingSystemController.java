package com.template.sbtemplate.controller;

import com.template.sbtemplate.constant.Constants;
import com.template.sbtemplate.dto.AddReview.AddReviewRequestDto;
import com.template.sbtemplate.dto.GetReview.GetReviewRequestDto;
import com.template.sbtemplate.response.success.ApiSuccess;
import com.template.sbtemplate.service.impl.RatingReviewService;
import io.micrometer.core.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@CrossOrigin()
@RestController
@RequestMapping(value = Constants.API_V1)
public class RatingSystemController {

    @Autowired
    protected RatingReviewService ratingReviewService;

    @GetMapping(
        value = Constants.REVIEW_GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiSuccess<Object>> get(@Valid @RequestParam(name = "sort_by", required = false) String sortby, @Valid @RequestParam(name = "id", required = false) String id, @Valid @RequestParam(name = "page", required = false, defaultValue = "0")
                    Integer page,
            @Valid
                    @RequestParam(
                            name = "size",
                            required = false,
                            defaultValue = Constants.PAGE_SIZE_DEFAULT)
                    Integer pageSize) {

        return ApiSuccess.ok(ratingReviewService.getReview(GetReviewRequestDto.builder().id(id).sortby(sortby).page(page).size(pageSize).build()));
    }

    @PostMapping(
        value = Constants.REVIEW_ADD,
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiSuccess<Object>> post(@RequestBody @Valid AddReviewRequestDto addReviewRequestDto) {
        return ApiSuccess.ok(ratingReviewService.addReview(addReviewRequestDto));
    }

    @GetMapping(
        value = Constants.RATING_GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiSuccess<Object>> getRatings() {
        return ApiSuccess.ok(ratingReviewService.getRatings());
    }
}
