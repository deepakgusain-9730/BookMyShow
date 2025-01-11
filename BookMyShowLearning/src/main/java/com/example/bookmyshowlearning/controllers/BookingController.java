package com.example.bookmyshowlearning.controllers;

import com.example.bookmyshowlearning.dtos.BookMovieRequestDto;
import com.example.bookmyshowlearning.dtos.BookMovieResponseDto;
import com.example.bookmyshowlearning.enums.ResponseStatus;
import com.example.bookmyshowlearning.models.Booking;
import com.example.bookmyshowlearning.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {

    private BookingService bookingService;

    @Autowired // Automatic finds the object if created otherwise create it and pass it here
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookMovieResponseDto bookTicket(BookMovieRequestDto bookMovieRequestDto) {
        BookMovieResponseDto bookMovieResponseDto = new BookMovieResponseDto();
        try {
            Booking booking = bookingService.bookingMovie(bookMovieRequestDto.getUserId(),
                    bookMovieRequestDto.getShowSeatIds(),
                    bookMovieRequestDto.getShowId());
            bookMovieResponseDto.setBookingId(booking.getId());
            bookMovieResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
            bookMovieResponseDto.setMessage("Booking Successfully Done.");
            bookMovieResponseDto.setAmount(booking.getAmount());
        }
        catch (Exception e) {
            bookMovieResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            bookMovieResponseDto.setMessage("Booking failed.");
        }
        return bookMovieResponseDto;
    }
}
