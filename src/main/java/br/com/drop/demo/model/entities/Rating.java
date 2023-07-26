package br.com.drop.demo.model.entities;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Integer id;

    @Column(name = "rating_product_note")
    @DecimalMin(value = "0", inclusive = true, message = "The minimum rating is 0")
    @DecimalMax(value = "10", inclusive = true, message = "The maximum rating is 10")
    private BigDecimal product_note;

    @Column(name = "rating_review")
    private String review;

    @Column(name = "rating_evaluation_date")
    private LocalDate evaluation_date;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


}
