package s20343.sri.sri6.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import s20343.sri.sri6.model.Movie;
import s20343.sri.sri6.model.Review;
import s20343.sri.sri6.repository.MovieRepository;
import s20343.sri.sri6.repository.ReviewRepository;

import java.util.List;

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

    // Handles the navigation mapping: Movie -> Reviews
    @SchemaMapping(typeName = "Movie", field = "reviews")
    public List<Review> getReviewsForMovie(Movie movie) {
        return reviewRepository.findByMovie_Id(movie.getId());
    }
}
