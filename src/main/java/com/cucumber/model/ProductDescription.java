package com.cucumber.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "product_description")
public class ProductDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String modelName;
    private Category category;
    private String manufacturer;
    private String description;
    private String filename;
    private boolean active;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "productDescription", cascade = CascadeType.ALL)
    private Set<Product> products;
}
