package br.com.drop.demo.service;


import br.com.drop.demo.model.dto.RatingDTO;
import br.com.drop.demo.model.entities.Rating;


public interface ProductService {

    Rating postReview(RatingDTO dto);
}
