package br.com.drop.demo.model.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "order")
    private List<Items> itemsList;

    @Column(name = "order_date")
    private LocalDate date;

    @Column(name = "order_total_value")
    private BigDecimal total_value;

    @Column(name = "order_payment_status")
    private String payment_status;

    @Column(name = "order_delivery_status")
    private String delivery_status;



    @PrePersist
    public void prePersist() {
        date = LocalDate.now();
    }




}
