package br.com.drop.demo.controller;



import br.com.drop.demo.model.entities.Product;
import br.com.drop.demo.model.exeption.BusinessRule;
import br.com.drop.demo.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Product save (@RequestBody Product product){
        return productRepository.save(product);
    }

    @GetMapping("/show/all")
    public List<Product> showAll(){
        return productRepository.findAll();
    }


    @GetMapping("/get/{product_id}")
    public Product getProductById(@PathVariable Integer product_id){
        return productRepository.findById(product_id)
                .orElseThrow(() -> new BusinessRule(HttpStatus.NOT_FOUND,"Id nao encontrado"));
    }
}
