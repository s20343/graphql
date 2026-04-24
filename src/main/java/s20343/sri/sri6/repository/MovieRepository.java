package s20343.sri.sri6.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import s20343.sri.sri6.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
