package com.example.bookmyshowlearning.models;

import com.example.bookmyshowlearning.enums.PaymentProvider;
import com.example.bookmyshowlearning.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payment extends BaseModel{
    private String refNumber;

    private int amount;

    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.ORDINAL)
    private PaymentProvider paymentProvider;
}
