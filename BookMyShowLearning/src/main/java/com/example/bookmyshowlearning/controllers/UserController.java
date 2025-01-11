package com.example.bookmyshowlearning.controllers;

import com.example.bookmyshowlearning.dtos.UserRequestDto;
import com.example.bookmyshowlearning.dtos.UserResponseDto;
import com.example.bookmyshowlearning.enums.ResponseStatus;
import com.example.bookmyshowlearning.models.User;
import com.example.bookmyshowlearning.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }


    public UserResponseDto signUp(UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = new UserResponseDto();
        try{
            User user = userService.singUp(
                    userRequestDto.getName(),
                    userRequestDto.getEmail(),
                    userRequestDto.getPassword()
            );
            userResponseDto.setUserId(user.getId());
            userResponseDto.setResponseMessage("User is created.");
            userResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch(Exception e){
            userResponseDto.setResponseMessage("User is not created.");
            userResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return userResponseDto;
    }

    public UserResponseDto singIn(UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = new UserResponseDto();
        try{
            User user = userService.singIn(
                    userRequestDto.getEmail(),
                    userRequestDto.getPassword()
            );
            userResponseDto.setUserId(user.getId());
            userResponseDto.setResponseMessage("Sing in.");
            userResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch(Exception e){
            userResponseDto.setResponseMessage("User not found.");
            userResponseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return userResponseDto;
    }
}
