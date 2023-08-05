package br.com.drop.demo.service.impl;


import br.com.drop.demo.exception.SenhaInvalidaException;
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

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final AddresRepository addresRepository;
    private final PersonalDateRepository personalDateRepository;
    private final ContactRepository contactRepository;


    @Transactional
    public Usuario save(UserDTO userDTO) {
        Usuario usuario = new Usuario();
        usuario.setName(userDTO.getName());
        usuario.setEmail(userDTO.getEmail());
        usuario.setPassword(userDTO.getPassword());
        System.out.println("salvando usuario");
        usuario = userRepository.save(usuario); // Salvando o usuário para obter o ID gerado automaticamente
        System.out.println("Usuario salvo");

        // Cria o endereço
        Address address = new Address();
        address.setCep("12345-678");
        address.setCity("São Paulo");
        address.setComplement("Apartment 123");
        address.setDistrict("Centro");
        address.setNation("Brazil");
        address.setResidential_number("456");
        address.setStreet("Rua Principal");
        address.setState("SP");

        // Associa o usuário ao endereço
        address.setUsuario(usuario);

        // Salva o endereço
        address = addresRepository.save(address);

        // Atualiza o ID do Address associado ao usuário
        usuario.setAddress_id(address);

        usuario = userRepository.save(usuario);


        PersonalData personalData = savePersonalData(usuario, address);
        personalData = personalDateRepository.save(personalData);

        // Atualizando o ID do PersonalData associado ao usuário
        usuario.setPersonal_data_id(personalData);

        // Atualizando o ID do Address associado ao usuário
        usuario.setAddress_id(address);

        usuario = userRepository.save(usuario); // Salvando novamente o usuário com as informações atualizadas

        return usuario;
    }

    private Address saveAddress(Usuario usuario) {
        Address address = new Address();
        address.setCep("");
        address.setCity("");
        address.setComplement("");
        address.setDistrict("");
        address.setNation("");
        address.setResidential_number("");
        address.setStreet("");
        address.setState("");
        address.setUsuario(usuario);
        return address;
    }

    private PersonalData savePersonalData(Usuario usuario, Address address) {
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

    public Usuario update(@PathVariable Integer user_id, @RequestBody Usuario usuario){
        return userRepository.findById(user_id)
                .map(update_user -> {
                    usuario.setId(update_user.getId());
                    userRepository.save(usuario);
                    return update_user;
                }).orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND, "Update failed, usuario not found or does not exist "));

    }



    public Optional<Address> getFullAddress(Integer address_id) {
        return addresRepository.findById(address_id);
    }


    public void deactivateAccount(Integer user_id) {
        userRepository.findById(user_id)
                .map( user -> {
                    user.set_active_user(false);
                    return userRepository.save(user);
                }).orElseThrow(() -> new BusinessRule(HttpStatus.NO_CONTENT, "Usuario not found."));

    }



    public UserDetails autenticar(Usuario usuario){

        UserDetails user = loadUserByUsername(usuario.getEmail());

        boolean senhasBatem = passwordEncoder.matches(usuario.getPassword(), user.getPassword());
        if(senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario not found"));

        String[] roles = usuario.isAdmin()
                ? new String[]{"USER","ADMIN"}
                : new String[]{"USER"};

        System.out.println(Arrays.toString(roles));

        boolean usuarioAtivo = usuario.is_active_user();
        boolean is_adm = usuario.isAdmin();
        return User
                .builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .accountLocked(usuario.isAdmin())
                .roles(roles)
                .disabled(!usuarioAtivo)
                .build();




    }
}
