package com.janith.repository;

import com.janith.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { //unique identifier of User is  id its type is Long
    public User findByEmail(String username);
}
