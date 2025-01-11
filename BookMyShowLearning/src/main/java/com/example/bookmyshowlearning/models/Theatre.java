package com.example.bookmyshowlearning.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Theatre extends BaseModel{

    private String name;

    @ManyToOne
    private Region region;

    @OneToMany
    private List<Screen> screens;
}
