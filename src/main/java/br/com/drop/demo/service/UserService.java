package br.com.drop.demo.service;


import br.com.drop.demo.model.dto.UserDTO;
import br.com.drop.demo.model.entities.Address;
import br.com.drop.demo.model.entities.Usuario;


import java.util.Optional;

public interface UserService {

    Usuario save(UserDTO userDTO);
    Optional<Address> getFullAddress(Integer address_id);
    void deactivateAccount(Integer user_id);
}
