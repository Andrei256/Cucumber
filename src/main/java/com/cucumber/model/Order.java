package com.cucumber.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ordr")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "date_of_delivery")
    private LocalDate dateOfDelivery;
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
//    @Column(name = "total_cost")
//    private float totalCost;
    private boolean complete;

    @ManyToOne
    @JoinColumn(name = "user_buyer_id")
    private User buyer;

/*    @ManyToOne
    @JoinColumn(name = "user_seller_id")
    private User seller;*/

    @OneToMany
    private List<Product> products;
}
