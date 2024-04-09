package com.hrbuddy.persistence.repositories;

import com.hrbuddy.persistence.entities.User;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class UserRepository extends Repository<User>{


    public UserRepository(EntityManager entityManager){
        super(entityManager);
        setType(User.class);
    }

    public Optional<User> getByEmail(String email){
        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
        return Optional.ofNullable(user);
    }
}
