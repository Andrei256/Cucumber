package com.cucumber.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ordr")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date_of_action")
    private LocalDate dateOfAction;

    @NotBlank(message = "Поле не может быть быть пустым")
    @Length(max = 255, message = "Поле слишком длинное(Больше 255 знаков)")
    private String address;

    @Pattern(regexp = "\\Q+375\\E(\\Q29\\E)?(\\Q33\\E)?(\\Q44\\E)?(\\Q25\\E)?[0-9]{7}", message = "Некорректный номер телефона")
    @Column(name = "phone_number")
    private String phoneNumber;

    private State state;

    private int cost;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @EqualsAndHashCode.Exclude
    @OneToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;
}
