package s20343.sri.sri6.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import s20343.sri.sri6.model.Movie;
import s20343.sri.sri6.model.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMovieIn(List<Movie> movies);

}
