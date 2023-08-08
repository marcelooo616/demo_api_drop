package br.com.drop.demo.controller;


import br.com.drop.demo.model.dto.ImageDTO;
import br.com.drop.demo.model.entities.Images;
import br.com.drop.demo.repository.ImageRepository;
import br.com.drop.demo.service.impl.ImagesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/image")

public class ImagesController {

    ImageRepository imageRepository;
    private ImagesServiceImpl imagesService;

    public ImagesController(ImageRepository imageRepository, ImagesServiceImpl imagesService) {
        this.imageRepository = imageRepository;
        this.imagesService = imagesService;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Images save(@RequestBody Images images){
        return imageRepository.save(images);
    }

    @GetMapping("/show/all")
    public List<ImageDTO> showAll(){
        return imagesService.findAllImagesDTO();
    }

}
