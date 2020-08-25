package com.cucumber.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "total_cost")
    private float totalCost;

    @OneToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ToString.Exclude
    @OneToMany
    private List<Offer> offers = new ArrayList<>();
}
