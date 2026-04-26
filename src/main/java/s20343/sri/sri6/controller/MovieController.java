package s20343.sri.sri6.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import s20343.sri.sri6.model.Movie;
import s20343.sri.sri6.model.Review;
import s20343.sri.sri6.repository.MovieRepository;
import s20343.sri.sri6.repository.ReviewRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @QueryMapping
    public List<Movie> movies() {
        return movieRepository.findAll();
    }

    @QueryMapping
    public Movie movieById(@Argument Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    @MutationMapping
    public Movie addMovie(@Argument String title, @Argument String director, @Argument Integer releaseYear) {
        Movie movie = Movie.builder()
                .title(title)
                .director(director)
                .releaseYear(releaseYear)
                .build();
        return movieRepository.save(movie);
    }

    @BatchMapping
    public Map<Movie, List<Review>> reviews(List<Movie> movies) {

        // Extract movie IDs
        List<Long> movieIds = movies.stream()
                .map(Movie::getId)
                .toList();

        // Fetch all reviews in ONE query
        List<Review> reviews = reviewRepository.findByMovie_IdIn(movieIds);

        // Group reviews by movie
        return reviews.stream()
                .collect(Collectors.groupingBy(Review::getMovie));
    }
//    @SchemaMapping(typeName = "Movie", field = "reviews")
//    public List<Review> getReviewsForMovie(Movie movie) {
//        return reviewRepository.findByMovie_Id(movie.getId());
//    }

}
