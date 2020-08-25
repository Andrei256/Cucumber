package com.cucumber.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotBlank(message = "Поле не может быть пустым")
    @Length(max = 32, message = "Поле слишком длинное(Больше 32 знаков)")
    private String heading;

    @NotBlank(message = "Поле не может быть пустым")
    @Length(max = 2048, message = "Поле слишком длинное(Больше 2048 знаков)")
    private String text;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;
}
