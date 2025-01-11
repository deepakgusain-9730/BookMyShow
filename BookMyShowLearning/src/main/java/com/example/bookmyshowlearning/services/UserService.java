package com.example.bookmyshowlearning.services;

import com.example.bookmyshowlearning.exceptions.RecordAlreadyAvailable;
import com.example.bookmyshowlearning.exceptions.RecordNotFoundException;
import com.example.bookmyshowlearning.models.User;
import com.example.bookmyshowlearning.repositories.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User singUp(String name, String email, String password) throws RecordAlreadyAvailable {

        Optional<User> availableUser = userRepository.findUserByEmail(email);
//        Checking if user is already available with the email
        if (availableUser.isPresent()) {
            throw new RecordAlreadyAvailable("User is already Available with email: " + email);
        }
//        Creating new user here
        User user = new User();
        user.setName(name);
        user.setEmail(email);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));
//        saving to db and returning back to controller
        return userRepository.save(user);
    }

    public User singIn(String email, String password) throws RecordNotFoundException {

        Optional<User> availableUser = userRepository.findUserByEmail(email);
//        Checking if user is already available with the email
        if (availableUser.isEmpty()) {
            throw new RecordNotFoundException("Record is not available with email: " + email);
        }
//        Creating new user here
        User user = new User();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return user;
        };
//        throw incorrect password error
        throw new RecordNotFoundException("Incorrect Password");


    }
}
