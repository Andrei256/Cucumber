package com.cucumber.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "ordr")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "time_of_delivery")
    private LocalDate dateOfDelivery;
    private String address;
    @Column(name = "total_cost")
    private float totalCost;
    private boolean complete;

    private User buyer;
    private Set<Product> products;

}
