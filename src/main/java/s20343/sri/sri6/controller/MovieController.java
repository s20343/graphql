package s20343.sri.sri6.controller;


import graphql.GraphQLException;
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
    public Movie addMovie(@Argument String title,
                          @Argument String director,
                          @Argument Integer releaseYear) {

        Movie movie = Movie.builder()
                .title(title)
                .director(director)
                .releaseYear(releaseYear)
                .build();

        return movieRepository.save(movie);
    }

    @MutationMapping
    public Movie updateMovie(@Argument Long id,
                             @Argument String title,
                             @Argument String director,
                             @Argument Integer releaseYear) {

        Movie toUpdate = movieRepository.findById(id)
                .orElseThrow(() -> new GraphQLException("Movie with id " + id + " not found"));

        if (title != null) toUpdate.setTitle(title);
        if (director != null) toUpdate.setDirector(director);
        if (releaseYear != null) toUpdate.setReleaseYear(releaseYear);

        return movieRepository.save(toUpdate);
    }

    @MutationMapping
    public Boolean deleteMovie(@Argument Long id) {
        if (!movieRepository.existsById(id)) return false;

        movieRepository.deleteById(id);
        return true;
    }
    @BatchMapping
    public Map<Movie, List<Review>> reviews(List<Movie> movies) {

        // 1. Fetch all reviews for the list of movies
        List<Review> reviews = reviewRepository.findByMovieIn(movies);

        // 2. Group the reviews directly by the Movie object
        Map<Movie, List<Review>> reviewsByMovie = reviews.stream()
                .collect(Collectors.groupingBy(Review::getMovie));

        // 3. Ensure movies with 0 reviews get an empty list instead of null
        movies.forEach(movie -> reviewsByMovie.putIfAbsent(movie, List.of()));

        return reviewsByMovie;
    }


}
