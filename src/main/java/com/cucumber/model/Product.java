package com.cucumber.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean active;
    private float cost;

    @ManyToMany
    @JoinTable(
            name = "usr_product",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn (name = "users_id" ) }
    )
    private Set<User> sellers;

    @ManyToOne
    @JoinColumn(name = "product_description_id")
    private ProductDescription productDescription;
}
