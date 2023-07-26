package br.com.drop.demo.repository;

import br.com.drop.demo.model.entities.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items, Integer> {
}
