package com.example.pizzastore.service;

import com.example.pizzastore.model.Pizza;
import com.example.pizzastore.repsitory.PizzaStoreRepository;
import com.example.pizzastore.service.exception.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PizzaStoreService {

    @Autowired
    private PizzaStoreRepository pizzaStoreRepository;

    @Cacheable("pizza")
    public List<Pizza> getAll(){
        return (List<Pizza>) pizzaStoreRepository.findAll();
    }

    public Pizza update(Pizza pizza){
//        pizzaStoreRepository.findById(pizza.getSlug()).orElseThrow(ItemNotFoundException::new);
        pizzaStoreRepository.save(pizza);
        return pizza;
    }

    public void delete(String id){
        pizzaStoreRepository.delete(pizzaStoreRepository.findById(id).orElseThrow(ItemNotFoundException::new));
    }

    public Pizza create(Pizza pizza){
//        if(pizzaStoreRepository.existsBySlug(pizza.getSlug()))
//            throw new ItemAlreadyExistException();
        return pizzaStoreRepository.save(pizza);
    }

    public List<Pizza> filterByName(String name){
        return pizzaStoreRepository.findAllByName(name);
    }
}
