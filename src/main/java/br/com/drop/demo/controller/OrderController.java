package br.com.drop.demo.controller;



import br.com.drop.demo.model.dto.ItemDescriptionDTO;
import br.com.drop.demo.model.dto.OrderDTO;
import br.com.drop.demo.model.dto.OrderDescriptionDTO;
import br.com.drop.demo.model.entities.Items;
import br.com.drop.demo.model.entities.Order;
import br.com.drop.demo.model.exeption.BusinessRule;
import br.com.drop.demo.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody OrderDTO dto){
        Order order = orderService.save(dto);
        return order.getId();
    }

    @GetMapping("{order_id}")
    public OrderDescriptionDTO getById(@PathVariable Integer order_id){
        return orderService.showOrder(order_id)
                .map(o -> convertOrderToOrderDescriptionDTO(o))
                .orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND, "Order not found"));

    }


    private OrderDescriptionDTO convertOrderToOrderDescriptionDTO(Order order){
        return OrderDescriptionDTO
                .builder()
                .code_order(order.getId())
                .user_id(order.getUsuario().getId())
                .date(order.getDate())
                .total_value(order.getTotal_value())
                .payment_status(order.getPayment_status())
                .delivery_status(order.getDelivery_status())
                .list_itens(convertItemsToItemsDTO(order.getItemsList()))
                .build();


    }

    private List<ItemDescriptionDTO> convertItemsToItemsDTO(List<Items> items){

        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }

        return items.stream().map(
                i -> ItemDescriptionDTO
                .builder()
                        .product_description(i.getProduct().getDescription())
                        .amount(i.getAmount())
                        .promotion_status(i.getProduct().isPromotion_status())
                        .value(i.getProduct().isPromotion_status() ? i.getProduct().getPromotion_price() : i.getProduct().getUnit_price())
                .build()
        ).collect(Collectors.toList());
    }
}
