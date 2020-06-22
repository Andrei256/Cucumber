package com.cucumber.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product_description")
public class ProductDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String category;
    private String manufacturer;
    private String description;

    private Set<Product> products;

}
