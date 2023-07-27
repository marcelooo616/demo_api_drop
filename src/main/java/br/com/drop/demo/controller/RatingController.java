package br.com.drop.demo.controller;



import br.com.drop.demo.model.dto.RatingDTO;
import br.com.drop.demo.model.entities.Rating;
import br.com.drop.demo.model.exeption.BusinessRule;
import br.com.drop.demo.repository.RatingRepository;
import br.com.drop.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingRepository ratingRepository;
    private final ProductService productService;




    @PostMapping("/post/review")
    public Rating save(@RequestBody RatingDTO ratingDTO){
        return productService.postReview(ratingDTO);
    }

    @GetMapping("/show/all")
    public List<RatingDTO> showAll(){
        List<Rating> ratingList = ratingRepository.findAll();

        if(CollectionUtils.isEmpty(ratingList)){
            return Collections.emptyList();
        }

        return ratingList.stream().map(
                rating -> RatingDTO
                .builder()
                        .product_note(rating.getProduct_note())
                        .review(rating.getReview())
                        .evaluation_date(rating.getEvaluation_date())
                        .product_id(rating.getProduct().getId())
                .build()
        ).collect(Collectors.toList());
    }

    @DeleteMapping("/{rating_id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteRating(@PathVariable Integer rating_id){
        ratingRepository.findById(rating_id)
                .map(rating -> {
                    ratingRepository.delete(rating);
                    return rating;
                }).orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND, "Rating nao encontrado!"));

    }

    @GetMapping("/{id}")
    public Rating rating(@PathVariable Integer id){
        return ratingRepository.findById(id).orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND, "Rating nao encontrado!"));
    }
}
