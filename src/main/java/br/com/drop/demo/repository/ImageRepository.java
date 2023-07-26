package br.com.drop.demo.repository;



import br.com.drop.demo.model.entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Images, Integer> {
}
