package br.com.drop.demo.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    private Integer addres_id;
    private String street;
    private String residential_number;
    private String complement;
    private String district;
    private String city;
    private String state;
    private String cep;
    private String nation;
    private Integer user_id;
}
