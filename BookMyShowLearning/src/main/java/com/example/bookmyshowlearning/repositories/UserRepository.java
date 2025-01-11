package com.example.bookmyshowlearning.repositories;

import com.example.bookmyshowlearning.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long aLong);

    Optional<User> findUserByEmail(String email);

    @Override
    User save(User user);
}

//JPA interface
//Hibernate is implementation of interface
