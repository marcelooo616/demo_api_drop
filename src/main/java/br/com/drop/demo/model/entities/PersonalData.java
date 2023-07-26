package br.com.drop.demo.model.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "personal_data")
public class PersonalData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String completed_name;

    @Column
    private String birthday;

    @Column
    private String gender;

    @Column
    private String cpf;

    @Column
    private String whatsapp;

    @Column
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;



}
