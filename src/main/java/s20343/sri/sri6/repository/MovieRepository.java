package s20343.sri.sri6.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import s20343.sri.sri6.model.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

//    @EntityGraph(attributePaths = {"reviews"})
//    List<Movie> findAll();

}
