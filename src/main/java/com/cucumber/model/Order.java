package com.cucumber.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ordr")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "date_of_action")
    private LocalDate dateOfAction;
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    private State state;
    private float cost;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;
}
