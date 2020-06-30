package com.cucumber.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
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

    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usr_product",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn (name = "user_id" ) }
    )
    private Set<User> sellers = new HashSet<>();

    @ManyToOne
//    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "product_description_id")
    private ProductDescription productDescription;
}
