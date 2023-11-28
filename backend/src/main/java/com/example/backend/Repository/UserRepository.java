package com.example.backend.Repository;

import com.example.backend.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);
}
