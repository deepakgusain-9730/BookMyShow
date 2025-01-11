package com.example.bookmyshowlearning.dtos;

import com.example.bookmyshowlearning.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private ResponseStatus responseStatus;

    private String responseMessage;

    private Long userId;
}
