package br.com.drop.demo.controller;




import br.com.drop.demo.model.entities.Contacts;
import br.com.drop.demo.model.exeption.BusinessRule;
import br.com.drop.demo.repository.ContactRepository;
import br.com.drop.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/usuario/contact")
public class ContactController {

    ContactRepository contactRepository;
    UserRepository userRepository;

    public ContactController(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/insert")
    public Contacts insert(@RequestBody Contacts contacts){
        return contactRepository.save(contacts);

    }

    @GetMapping("/all")
    public List<Contacts> showAll(){
        return contactRepository.findAll();
    }

    /*@GetMapping("/{user_id}")
    public List<Contacts> searchForId(@PathVariable("user_id") Integer user_id){
        Usuario usuario = userRepository.findById(user_id).orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND,"usuario não encontrado" ));
        return contactRepository.findByUserId(user_id);
    }*/

    @DeleteMapping("/{contact_id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable  Integer contact_id){
        contactRepository.findById(contact_id)
                .map(contact -> {
                    contactRepository.delete(contact);
                    return contact;
                }).orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND, "Deletion failed, usuario not found or does not exist "));

    }


    @PutMapping("/{contact_id}/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Contacts update(@PathVariable Integer contact_id, @RequestBody Contacts contacts){
        return contactRepository.findById(contact_id)
                .map(update_contact -> {
                    contacts.setId(update_contact.getId());
                    contactRepository.save(contacts);
                    return update_contact;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Update failed, usuario not found or does not exist "));

    }


}
