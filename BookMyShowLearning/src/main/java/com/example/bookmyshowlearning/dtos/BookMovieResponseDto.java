package com.example.bookmyshowlearning.dtos;

import com.example.bookmyshowlearning.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMovieResponseDto {

    private ResponseStatus responseStatus;

    private Long bookingId;

    private int amount;

    private String message;
}
