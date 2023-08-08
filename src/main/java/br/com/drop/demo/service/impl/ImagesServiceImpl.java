package br.com.drop.demo.service.impl;

import br.com.drop.demo.model.dto.ImageDTO;
import br.com.drop.demo.model.entities.Images;
import br.com.drop.demo.repository.ImageRepository;
import br.com.drop.demo.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImagesServiceImpl  implements ImageService {


    @Autowired
    private ImageRepository imageRepository;

    public List<ImageDTO> findAllImagesDTO() {

        List<Images> img = imageRepository.findAll();


        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Images, ImageDTO>() {

            @Override
            protected void configure(){
                map().setProductId(source.getProduct().getId());
                map().setImageUrl(source.getImage_url());

            }
        });

        return img.stream()
                .map(images -> modelMapper.map(images, ImageDTO.class))
                .collect(Collectors.toList());
    }
}
