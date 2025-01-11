package com.example.bookmyshowlearning.services;

import com.example.bookmyshowlearning.models.Show;
import com.example.bookmyshowlearning.models.ShowSeat;
import com.example.bookmyshowlearning.models.ShowSeatType;
import com.example.bookmyshowlearning.repositories.ShowSeatRepository;
import com.example.bookmyshowlearning.repositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculator {

    private ShowSeatTypeRepository showSeatTypeRepository;

    @Autowired
    PriceCalculator(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public int calculatePrice(List<ShowSeat> showSeats, Show show) {
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);
        int amount = 0;
        for(ShowSeat s: showSeats) {

            for (ShowSeatType showSeatType : showSeatTypes) {
                if(s.getSeat().getSeatType().equals(showSeatType.getSeatType())) {
                    amount += showSeatType.getPrice();
                }

            }
        }
        return amount;
    }
}
