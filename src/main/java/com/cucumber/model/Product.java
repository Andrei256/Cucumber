package com.cucumber.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float cost;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User seller;

    @ManyToOne
//    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "product_description_id")
    private ProductDescription productDescription;
}
