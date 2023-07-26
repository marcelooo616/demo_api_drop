package br.com.drop.demo.repository;


import drop.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Integer> {



    Optional<Usuario> findByEmail(String email);
}
