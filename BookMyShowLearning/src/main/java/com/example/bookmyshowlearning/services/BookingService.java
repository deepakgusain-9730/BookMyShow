package com.example.bookmyshowlearning.services;

import com.example.bookmyshowlearning.enums.BookingStatus;
import com.example.bookmyshowlearning.enums.ShowSeatStatus;
import com.example.bookmyshowlearning.exceptions.RecordAlreadyOccupiedException;
import com.example.bookmyshowlearning.exceptions.RecordNotFoundException;
import com.example.bookmyshowlearning.models.Booking;
import com.example.bookmyshowlearning.models.Show;
import com.example.bookmyshowlearning.models.ShowSeat;
import com.example.bookmyshowlearning.models.User;
import com.example.bookmyshowlearning.repositories.BookingRepository;
import com.example.bookmyshowlearning.repositories.ShowRepository;
import com.example.bookmyshowlearning.repositories.ShowSeatRepository;
import com.example.bookmyshowlearning.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculator priceCalculator;

    @Autowired
    BookingService(BookingRepository bookingRepository,
                   UserRepository userRepository,
                   ShowRepository showRepository,
                   ShowSeatRepository showSeatRepository,
                   PriceCalculator priceCalculator) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculator = priceCalculator;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookingMovie(Long userId,
                                List<Long> showSeatIds,
                                Long showId
                                ) throws RecordNotFoundException, RecordAlreadyOccupiedException {
//        Find user by id
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new RecordNotFoundException("Invalid UserId");
        }

        User bookedBy = userOptional.get();
        Optional<Show> showOptional = showRepository.findById(showId);

//        Check if show available or not
        if(showOptional.isEmpty()){
            throw new RecordNotFoundException("Invalid ShowId");
        }
        Show bookedShow = showOptional.get();

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
        for(ShowSeat showSeat : showSeats){
//            Check if seats are blocked or booked
            if(!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
                    (showSeat.getShowSeatStatus().equals(ShowSeatStatus.BOOKED)) &&
                    (Duration.between(showSeat.getLockedAt().toInstant(), new Date().toInstant()).toMinutes()<15))){
                throw new RecordAlreadyOccupiedException("Seats are already occupied");
            }
        }

        List<ShowSeat> bookedShowSeats = new ArrayList<>();
        for(ShowSeat showSeat : showSeats){
//            Blocked the seats will mark booked once payment api will call
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            showSeat.setLockedAt(new Date());
            bookedShowSeats.add(showSeatRepository.save(showSeat));
        }

//        Creating booking object
        Booking booking = new Booking();
        booking.setBookedAt(new Date());
        booking.setShow(bookedShow);
        booking.setShowSeats(bookedShowSeats);
        booking.setUser(bookedBy);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setShow(bookedShow);
        booking.setAmount(priceCalculator.calculatePrice(bookedShowSeats, bookedShow));
        booking.setPayments(new ArrayList<>());
        booking = bookingRepository.save(booking);
        return booking;

    }
}
