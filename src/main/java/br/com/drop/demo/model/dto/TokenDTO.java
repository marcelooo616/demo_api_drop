package br.com.drop.demo.model.dto;

import br.com.drop.demo.model.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    Usuario usuario;
    private String token;

}
