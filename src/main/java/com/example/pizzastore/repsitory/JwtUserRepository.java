package com.example.pizzastore.repsitory;


import com.example.pizzastore.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JwtUserRepository extends CrudRepository<User, Integer> {

    List<User> findAll();

    User findUserByEmail(String email);
}
