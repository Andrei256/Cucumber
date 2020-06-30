package com.cucumber.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @JoinColumn(name = "user_id")
    private User buyer;

    @OneToMany
    @JoinColumn(name = "product_id")
    private Set<Product> products = new HashSet<>();
}
