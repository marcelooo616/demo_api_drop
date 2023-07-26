package br.com.drop.demo.controller;

import br.com.drop.demo.exception.SenhaInvalidaException;
import br.com.drop.demo.jwt.JwtService;
import br.com.drop.demo.model.dto.CredenciaisDTO;
import br.com.drop.demo.model.dto.TokenDTO;
import br.com.drop.demo.model.dto.UserDTO;
import br.com.drop.demo.model.entities.Usuario;
import br.com.drop.demo.model.exeption.BusinessRule;
import br.com.drop.demo.repository.UserRepository;
import br.com.drop.demo.service.UserService;
import br.com.drop.demo.service.impl.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuario")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario save(@Valid @RequestBody UserDTO userDTO){
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encryptedPassword);

        return  userDetailService.save(userDTO);

    }


    @PostMapping("/auth")
    public TokenDTO authenticateUser(@RequestBody CredenciaisDTO credenciaisDTO){
        try {
            Usuario usuario = Usuario.builder()
                    .email(credenciaisDTO.getEmail())
                    .password(credenciaisDTO.getPassword())
                    .build();

            UserDetails authenticatedUser = userDetailService.autenticar(usuario);

            if (!authenticatedUser.isEnabled()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Inactive user. Token cannot be generated.");
            }




            String token = jwtService.gerarToken(usuario);

            return new TokenDTO(usuario.getEmail(),token);

        }catch (UsernameNotFoundException | SenhaInvalidaException e){

            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED,e.getMessage());

        }

    }




    @GetMapping("/all")
    public List<Usuario> showAll(){
        return userRepository.findAll(Sort.by("name"));
    }

    @GetMapping("/{user_id}")
    public Usuario searchForId(@PathVariable("user_id") Integer user_id){
        return userRepository.findById(user_id)
                .orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND, "Usuario not found"));

    }

    @GetMapping("/filtered/search")
    public List<Usuario> filteredSearch(Usuario filter){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filter, matcher);
        return userRepository.findAll(example);

    }

    @PutMapping("/{user_id}/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Usuario update(@PathVariable Integer user_id, @RequestBody Usuario usuario){
        return userRepository.findById(user_id)
                .map(update_user -> {
                    usuario.setId(update_user.getId());
                    String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
                    usuario.setPassword(senhaCriptografada);
                    userRepository.save(usuario);
                    return update_user;
    }).orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND, "Update failed, usuario not found or does not exist "));

    }


    @PatchMapping("/{user_id}/deactivate/account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivateUser(@PathVariable Integer user_id){
        userService.deactivateAccount(user_id);

    }







}
