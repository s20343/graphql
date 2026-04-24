package s20343.sri.sri6.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import s20343.sri.sri6.model.Movie;
import s20343.sri.sri6.model.Review;
import s20343.sri.sri6.repository.MovieRepository;
import s20343.sri.sri6.repository.ReviewRepository;


import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    private void initData() {
        // Create Movies first
        Movie m1 = Movie.builder().title("Inception").director("Christopher Nolan").releaseYear(2010).build();
        Movie m2 = Movie.builder().title("The Matrix").director("The Wachowskis").releaseYear(1999).build();
        List<Movie> movies = Arrays.asList(m1, m2);
        movieRepository.saveAll(movies);

        // Create Reviews and assign them to Movies
        Review r1 = Review.builder().reviewerName("Alice").content("Mind-blowing!").rating(5).movie(m1).build();
        Review r2 = Review.builder().reviewerName("Bob").content("A bit confusing, but great visuals.").rating(4).movie(m1).build();
        Review r3 = Review.builder().reviewerName("Charlie").content("A sci-fi masterpiece.").rating(5).movie(m2).build();

        List<Review> reviews = Arrays.asList(r1, r2, r3);
        reviewRepository.saveAll(reviews);

        log.info("Movie Data initialized!");
    }
}
