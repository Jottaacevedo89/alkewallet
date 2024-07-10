package com.javiera.alke.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.javiera.alke.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId); 
}
