package br.com.drop.demo.controller;



import br.com.drop.demo.model.entities.PersonalData;
import br.com.drop.demo.model.entities.Usuario;
import br.com.drop.demo.model.exeption.BusinessRule;
import br.com.drop.demo.repository.PersonalDateRepository;
import br.com.drop.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/personal/data")
public class PersonalDataController {

    PersonalDateRepository personalDataRepository;
    UserRepository userRepository;

    public PersonalDataController(PersonalDateRepository personalDataRepository, UserRepository userRepository) {
        this.personalDataRepository = personalDataRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonalData save(@RequestBody PersonalData data){
        return personalDataRepository.save(data);
    }



    @GetMapping("/usuario/{user_id}")
    public PersonalData data(@PathVariable("user_id") Integer user_id){
        Usuario usuario = userRepository.findById(user_id).orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND,"usuario não encontrado" ));
        return personalDataRepository.findById(usuario.getPersonal_data_id().getId()).orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND,"dados não encontrado" ));

    }



}
