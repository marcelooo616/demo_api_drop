package br.com.drop.demo.repository;


import br.com.drop.demo.model.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
}
