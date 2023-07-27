package br.com.drop.demo.controller;


import br.com.drop.demo.model.entities.Images;
import br.com.drop.demo.repository.ImageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/image")
public class ImagesController {

    ImageRepository imageRepository;

    public ImagesController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Images save(@RequestBody Images images){
        return imageRepository.save(images);
    }

    @GetMapping("/show/all")
    public List<Images> showAll(){
        return imageRepository.findAll();
    }
}
