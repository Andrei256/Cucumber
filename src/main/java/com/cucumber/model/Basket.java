package com.cucumber.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "total_cost")
    private float totalCost;

    private User buyer;
    private Set<Product> products;

}
