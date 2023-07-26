package br.com.drop.demo.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDescriptionDTO {

    private String product_description;
    private BigDecimal value;
    private Integer amount;
    private boolean promotion_status;

}
