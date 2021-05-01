package com.template.sbtemplate.repositories;

import com.template.sbtemplate.constant.Constants;
import com.template.sbtemplate.model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    Review findFirstById(String id);

    List<Review> findAllByOrderByCreatedOnDesc(Pageable pageable);

    List<Review> findAllByOrderByRatingDesc(Pageable pageable);

    @Query(nativeQuery = true, value = Constants.SqlQueries.COUNT_AVERAGE_RATING)
    Tuple findCountAverageRating();

}
