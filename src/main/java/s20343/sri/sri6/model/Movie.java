package s20343.sri.sri6.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    private String title;
    private String director;
    private Integer releaseYear;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Review> reviews;
}
