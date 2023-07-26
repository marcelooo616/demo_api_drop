package br.com.drop.demo.repository;

import drop.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(" select o from Order o left join fetch o.itemsList where o.id = :id ")
    Optional<Order> findByIdFetchItens(@Param("id") Integer id);
}
