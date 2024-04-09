package com.hrbuddy.services;

import com.hrbuddy.persistence.Database;
import com.hrbuddy.persistence.entities.User;
import com.hrbuddy.persistence.repositories.UserRepository;
import com.hrbuddy.services.dto.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UserService {


    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private UserService() {
    }

    public static UserDTO login(UserDTO dto){
        return Database.doInTransaction(entityManager -> {
            UserRepository repository = new UserRepository(entityManager);
            Optional<User> user = repository.getByEmail(dto.getEmail());
            if (user.isEmpty()){
                throw new IllegalArgumentException("Wrong Credentials. Email or password are incorrect");
            }
            String password = user.get().getPassword();
            if (!passwordEncoder.matches(dto.getPassword(), password)){
                throw new IllegalArgumentException("Wrong Credentials. Email or password are incorrect");
            }
            return UserDTO.of(user.get());
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
