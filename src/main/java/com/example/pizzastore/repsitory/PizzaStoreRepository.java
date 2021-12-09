package com.example.pizzastore.repsitory;


import com.example.pizzastore.model.Pizza;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PizzaStoreRepository extends CrudRepository<Pizza,String> {

    boolean existsBySlug(String name);
    List<Pizza> findAll(Sort date);
    @Override
    default List<Pizza> findAll() {
        return findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    List<Pizza> findAllByName(String name,Sort sort);

    default List<Pizza> findAllByName(String name){
        return findAllByName(name,Sort.by(Sort.Direction.DESC, "date"));
    }


}
