package br.com.drop.demo.model.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Integer id;

    @Column
    private String name;

    @Column
    @NotEmpty(message = "Campo obrigatorio")
    private String email;

    @Column
    @NotEmpty(message = "Campo obrigatorio")
    private String password;

    @Column
    private boolean is_active_user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_data_id")
    @JsonIgnore
    private PersonalData personal_data_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @JsonIgnore
    private Address address_id;

    @OneToMany(mappedBy = "usuario_id", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Contacts> contacts;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonIgnore
    private  List<Order> orders;

    @Column
    private boolean admin;


    @PrePersist
    public void prePersist() {
        is_active_user = true;
    }
}
