package com.hrbuddy.services;

import com.hrbuddy.persistence.Database;
import com.hrbuddy.persistence.entities.User;
import com.hrbuddy.persistence.repositories.UserRepository;
import com.hrbuddy.services.dto.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserService {


    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private UserService() {
    }

    public static UserDTO login(UserDTO dto){
        return Database.doInTransaction(entityManager -> {
            UserRepository repository = new UserRepository(entityManager);
            User user = repository.getByEmail(dto.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Wrong Credentials. Email or password are incorrect"));

            String password = user.getPassword();
            if (!passwordEncoder.matches(dto.getPassword(), password)){
                throw new IllegalArgumentException("Wrong Credentials. Email or password are incorrect");
            }
            return UserDTO.of(user);
        });
    }

    public static UserDTO register(UserDTO dto){
        return Database.doInTransaction(entityManager -> {
            UserRepository repository = new UserRepository(entityManager);
            User user = UserDTO.toUser(dto);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            repository.create(user);
            return UserDTO.of(user);
        });
    }
}
