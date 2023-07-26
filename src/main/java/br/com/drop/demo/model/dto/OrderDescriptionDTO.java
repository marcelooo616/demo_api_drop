package br.com.drop.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDescriptionDTO {

    private Integer code_order;
    private Integer user_id;
    private LocalDate date;
    private BigDecimal total_value;
    private String payment_status;
    private String delivery_status;
    private List<ItemDescriptionDTO> list_itens;
}
