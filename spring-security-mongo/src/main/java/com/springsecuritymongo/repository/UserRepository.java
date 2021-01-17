package com.springsecuritymongo.repository;

import java.util.Optional;

import com.springsecuritymongo.models.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}
