package br.com.drop.demo.model.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String street;

    @Column
    private String residential_number;

    @Column
    private String complement;

    @Column
    private String district;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String cep;

    @Column
    private String nation;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Usuario usuario;


}
