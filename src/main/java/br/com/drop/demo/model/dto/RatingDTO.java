package br.com.drop.demo.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDTO {


    @DecimalMin(value = "0", inclusive = true, message = "The minimum rating is 0")
    @DecimalMax(value = "10", inclusive = true, message = "The maximum rating is 10")
    private BigDecimal product_note;
    private String review;
    private Integer product_id;
    private LocalDate evaluation_date;


}
