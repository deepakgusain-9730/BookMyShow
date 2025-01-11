package com.example.bookmyshowlearning.models;

import com.example.bookmyshowlearning.enums.Feature;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Screen extends BaseModel{
    private String name;

//    to check which type of field it is in relationship wise
    @OneToMany
    private List<Seat> seats;

//    This means this enum should be created a table in db
    @Enumerated(EnumType.ORDINAL)
//    To create new table for this relationship
    @ElementCollection
    private List<Feature> features;
}
