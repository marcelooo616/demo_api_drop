package br.com.drop.demo.repository;


import drop.model.entities.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactRepository extends JpaRepository<Contacts, Integer > {

    /*@Query("SELECT c FROM Contacts c WHERE c.user_id.id = :userId")
    List<Contacts> findByUserId(@Param("userId") Integer userId);*/
}
