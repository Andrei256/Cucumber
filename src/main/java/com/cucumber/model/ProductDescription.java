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

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "productDescription")
    private Set<Product> products;
}
