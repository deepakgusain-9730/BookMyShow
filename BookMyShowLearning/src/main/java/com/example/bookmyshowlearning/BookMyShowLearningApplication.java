package com.example.bookmyshowlearning;

import com.example.bookmyshowlearning.controllers.UserController;
import com.example.bookmyshowlearning.dtos.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication
public class BookMyShowLearningApplication implements CommandLineRunner {

    @Autowired
    private UserController userController;


    @Override
    public void run(String ...args) throws Exception{
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("deepak.password1@gmail.com");
        userRequestDto.setName("Password");
        userRequestDto.setPassword("1234");
        userController.signUp(userRequestDto);
    }

    public static void main(String[] args) {
        SpringApplication.run(BookMyShowLearningApplication.class, args);
    }

}
