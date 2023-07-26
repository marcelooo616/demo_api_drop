package br.com.drop.demo.repository;


import br.com.drop.demo.model.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddresRepository extends JpaRepository<Address, Integer> {
}
