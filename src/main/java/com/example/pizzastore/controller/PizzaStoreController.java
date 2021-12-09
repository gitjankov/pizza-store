package com.example.pizzastore.controller;

import com.example.pizzastore.dto.ApiResponse;
import com.example.pizzastore.model.Pizza;
import com.example.pizzastore.repsitory.PizzaStoreRepository;
import com.example.pizzastore.service.PizzaStoreService;
import com.example.pizzastore.service.exception.ItemAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/menu")
public class PizzaStoreController {


    private CacheManager cacheManager;
    private PizzaStoreService pizzaStoreService;
    private PizzaStoreRepository pizzaStoreRepository;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Autowired
    public PizzaStoreController(PizzaStoreService pizzaStoreService,PizzaStoreRepository pizzaStoreRepository,
                                CacheManager cacheManager){
        this.pizzaStoreService = pizzaStoreService;
        this.pizzaStoreRepository = pizzaStoreRepository;
        this.cacheManager = cacheManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Pizza> getAll() {
        return pizzaStoreService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST,path = "/create")
    public ResponseEntity<?> create(@RequestBody Pizza pizza){
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<Pizza>> violations = validator.validate(pizza);
        for (ConstraintViolation<Pizza> violation : violations) {
            sb.append(violation.getMessage()).append("\r\n");
        }
        if(sb.length() > 0){
            return new ResponseEntity(sb.toString(),
                    HttpStatus.BAD_REQUEST);
        }
        else if(pizzaStoreRepository.existsBySlug(pizza.getSlug())){
            return new ResponseEntity("Item already exist, slug must be uniq",
                    HttpStatus.BAD_REQUEST);
        }
        cacheManager.getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
        return ResponseEntity.ok(pizzaStoreService.create(pizza));
    }

    @RequestMapping(method = RequestMethod.PUT,path = "/update")
    public ResponseEntity<?> update(@RequestBody Pizza pizza){
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<Pizza>> violations = validator.validate(pizza);
        for (ConstraintViolation<Pizza> violation : violations) {
            sb.append(violation.getMessage()).append("\r\n");
        }
        if(sb.length() > 0){
            return new ResponseEntity(sb.toString(),
                    HttpStatus.BAD_REQUEST);
        }
        else if(!pizzaStoreRepository.existsBySlug(pizza.getSlug())){
            return new ResponseEntity("Item does not exist, insert item first",
                    HttpStatus.BAD_REQUEST);
        }
        cacheManager.getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
        return ResponseEntity.ok(pizzaStoreService.update(pizza));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    public void delete(@PathVariable String id){
        cacheManager.getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
        pizzaStoreService.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/{name}")
    public List<Pizza> filter(@PathVariable String name){
        return pizzaStoreService.filterByName(name);
    }


}
