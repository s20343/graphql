package s20343.sri.sri6.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import s20343.sri.sri6.model.Movie;
import s20343.sri.sri6.model.Review;
import s20343.sri.sri6.repository.MovieRepository;
import s20343.sri.sri6.repository.ReviewRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    @QueryMapping
    public List<Review> reviews() {
        return reviewRepository.findAll();
    }

    @QueryMapping
    public Review reviewById(@Argument Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @MutationMapping
    public Review addReview(@Argument String reviewerName,
                            @Argument String content,
                            @Argument Integer rating,
                            @Argument Long movieId) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie with id: " + movieId + " not found"));

        Review review = Review.builder()
                .reviewerName(reviewerName)
                .content(content)
                .rating(rating)
                .movie(movie)
                .build();

        return reviewRepository.save(review);
    }

    @MutationMapping
    public Review updateReview(@Argument Long id,
                               @Argument String reviewerName,
                               @Argument String content,
                               @Argument Integer rating) {

        Review toUpdate = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review with id: " + id + " not found"));

        if (reviewerName != null) toUpdate.setReviewerName(reviewerName);
        if (content != null) toUpdate.setContent(content);
        if (rating != null) toUpdate.setRating(rating);

        return reviewRepository.save(toUpdate);
    }

    @MutationMapping
    public Boolean deleteReview(@Argument Long id) {
        if (!reviewRepository.existsById(id)) return false;

        reviewRepository.deleteById(id);
        return true;
    }
}
