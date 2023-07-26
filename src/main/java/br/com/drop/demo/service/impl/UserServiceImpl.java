package br.com.drop.demo.service.impl;


import br.com.drop.demo.model.dto.UserDTO;
import br.com.drop.demo.model.entities.Address;
import br.com.drop.demo.model.entities.Contacts;
import br.com.drop.demo.model.entities.PersonalData;
import br.com.drop.demo.model.entities.Usuario;
import br.com.drop.demo.model.exeption.BusinessRule;
import br.com.drop.demo.repository.AddresRepository;
import br.com.drop.demo.repository.ContactRepository;
import br.com.drop.demo.repository.PersonalDateRepository;
import br.com.drop.demo.repository.UserRepository;
import br.com.drop.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final AddresRepository addresRepository;
    private final PersonalDateRepository personalDateRepository;
    private final ContactRepository contactRepository;

    @Override
    @Transactional
    public Usuario save(UserDTO userDTO) {

        Usuario usuario = new Usuario();
        usuario.setName(userDTO.getName());
        usuario.setEmail(userDTO.getEmail());
        usuario.setPassword(userDTO.getPassword());
        userRepository.save(usuario);
        Address address = addresRepository.save(saveAddress(usuario));
        PersonalData personalData = personalDateRepository.save(savePersonalData(usuario, address));
        Contacts contacts = contactRepository.save(saveContact(usuario));
        return usuario;
    }

    @Override
    public Optional<Address> getFullAddress(Integer address_id) {
        return addresRepository.findById(address_id);
    }

    @Override
    public void deactivateAccount(Integer user_id) {
        userRepository.findById(user_id)
                .map( user -> {
                    user.set_active_user(false);
                    return userRepository.save(user);
                }).orElseThrow(() -> new BusinessRule(HttpStatus.NO_CONTENT, "Usuario not found."));

    }

    private Address saveAddress(Usuario usuario_id){
        Address address = new Address();
        address.setCep("");
        address.setCity("");
        address.setComplement("");
        address.setDistrict("");
        address.setNation("");
        address.setResidential_number("");
        address.setStreet("");
        address.setState("");
        address.setUsuario(usuario_id);
        return address;

    }

    private PersonalData savePersonalData(Usuario usuario, Address address){
        PersonalData personalData = new PersonalData();
        personalData.setCompleted_name("");
        personalData.setBirthday("");
        personalData.setGender("");
        personalData.setCpf("");
        personalData.setWhatsapp("");
        personalData.setEmail(usuario.getEmail());
        personalData.setUsuario(usuario);
        personalData.setAddress(address);
        return personalData;

    }

    private Contacts saveContact(Usuario usuario){
        Contacts contacts = new Contacts();
        contacts.setUsuario_id(usuario);
        contacts.setType("Email");
        contacts.setValue(usuario.getEmail());
        contacts.setPreferential(true);
        contacts.setObservation("");
        return contacts;
    }


}
