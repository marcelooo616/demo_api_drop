package br.com.drop.demo.service;



import br.com.drop.demo.model.dto.OrderDTO;
import br.com.drop.demo.model.entities.Order;


import java.util.Optional;

public interface OrderService {

    Order save(OrderDTO dto);

    Optional<Order> showOrder(Integer id);
}
