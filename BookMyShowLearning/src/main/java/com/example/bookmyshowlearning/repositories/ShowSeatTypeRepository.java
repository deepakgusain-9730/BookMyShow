package com.example.bookmyshowlearning.repositories;

import com.example.bookmyshowlearning.models.Show;
import com.example.bookmyshowlearning.models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Long> {

    List<ShowSeatType> findAllByShow(Show show);
}
