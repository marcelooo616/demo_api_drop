package br.com.drop.demo.model.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product {


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(length = 265)
    private String description;

    @Column
    private boolean promotion_status;

    @Column
    private Integer stock;

    @Column(precision = 20, scale = 2)
    private BigDecimal unit_price;

    @Column(precision = 20, scale = 2)
    private BigDecimal promotion_price;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Images> images;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Rating> ratingList;


}
