package com.cucumber.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Поле не может быть пустым")
    @Length(max = 128, message = "Поле слишком длинное(Больше 128 знаков)")
    private String modelName;

    private Category category;

    @NotBlank(message = "Поле не может быть пустым")
    @Length(max = 64, message = "Поле слишком длинное(Больше 64 знаков)")
    private String manufacturer;

    @NotBlank(message = "Поле не может быть пустым")
    @Length(max = 1024, message = "Поле слишком длинное(Больше 1024 знаков)")
    private String description;

    private String filename;

    private boolean active;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Offer> offers;
}
