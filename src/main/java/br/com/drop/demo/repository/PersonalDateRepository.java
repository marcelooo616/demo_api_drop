package br.com.drop.demo.repository;


import drop.model.entities.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalDateRepository extends JpaRepository<PersonalData, Integer> {

}
