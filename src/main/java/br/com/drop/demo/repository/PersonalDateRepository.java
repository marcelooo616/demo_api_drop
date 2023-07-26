package br.com.drop.demo.repository;


import br.com.drop.demo.model.entities.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalDateRepository extends JpaRepository<PersonalData, Integer> {

}
