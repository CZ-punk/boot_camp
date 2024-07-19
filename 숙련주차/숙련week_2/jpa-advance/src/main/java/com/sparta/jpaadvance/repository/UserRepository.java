package com.sparta.jpaadvance.repository;

import com.sparta.jpaadvance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}
