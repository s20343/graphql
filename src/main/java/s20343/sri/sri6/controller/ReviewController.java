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


}
