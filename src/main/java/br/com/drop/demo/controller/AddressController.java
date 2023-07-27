package br.com.drop.demo.controller;



import br.com.drop.demo.model.dto.AddressDTO;
import br.com.drop.demo.model.entities.Address;
import br.com.drop.demo.model.entities.Usuario;
import br.com.drop.demo.repository.AddresRepository;
import br.com.drop.demo.repository.UserRepository;
import br.com.drop.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario/address")
public class AddressController {

    AddresRepository addresRepository;
    UserRepository userRepository;
    UserService userService;

    public AddressController(AddresRepository addresRepository, UserRepository userRepository, UserService userService) {
        this.addresRepository = addresRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public Address save(@RequestBody Address address){
        return addresRepository.save(address);
    }


    @GetMapping("/all")
    public List<AddressDTO> showAll(){
        List<Address> listAddress = addresRepository.findAll();

        if(CollectionUtils.isEmpty(listAddress)){
            return Collections.emptyList();
        }

        return listAddress.stream().map(
                address -> AddressDTO
                    .builder()
                        .addres_id(address.getId())
                        .street(address.getStreet())
                        .residential_number(address.getResidential_number())
                        .complement(address.getComplement())
                        .district(address.getDistrict())
                        .city(address.getCity())
                        .state(address.getState())
                        .cep(address.getCep())
                        .nation(address.getNation())
                        .user_id(address.getUsuario().getId())
                .build()
        ).collect(Collectors.toList());
    }


    @GetMapping("/find/{user_id}")
    public AddressDTO getById(@PathVariable Integer user_id){
        Usuario usuario = userRepository.findById(user_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"usuario not found" ));
        return userService.getFullAddress(usuario.getPersonal_data_id().getAddress().getId())
                .map( a -> convertToDTO(a))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"usuario not found" ));
    }

    private AddressDTO convertToDTO(Address address){
        return AddressDTO
                .builder()
                .addres_id(address.getId())
                .street(address.getStreet())
                .residential_number(address.getResidential_number())
                .complement(address.getComplement())
                .district(address.getDistrict())
                .city(address.getCity())
                .state(address.getState())
                .cep(address.getCep())
                .nation(address.getNation())
                .user_id(address.getUsuario().getId())
                .build();
    }



   /* @PutMapping("/{user_id}/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Address address (@PathVariable Integer user_id, @RequestBody Address address){

        Usuario usuario = userRepository.findById(user_id).orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND,"usuario not found" ));

        return addresRepository.findById(usuario.getPersonalData_data().getAddress().getId())
                .map(update_address -> {
                    address.setId(update_address.getId());
                    addresRepository.save(address);
                    return update_address;
                }).orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND, "Update failed, usuario not found or does not exist "));
    }

    @PutMapping("/{user_id}/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Address address (@PathVariable Integer user_id, @RequestBody Address address){

        Usuario usuario = userRepository.findById(user_id).orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND,"usuario not found" ));

        return addresRepository.findById(address.getId())
                .map(update_address -> {
                    address.setId(update_address.getId());
                    addresRepository.save(address);
                    return update_address;
                }).orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND, "Update failed, usuario not found or does not exist "));
    }*/
}
