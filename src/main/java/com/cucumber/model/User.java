package com.cucumber.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String text;
    @Column(name = "lik")
    private int like;
    private boolean active;
    @Column(name = "activation_code")
    private String activationCode;

    private Set<Role> roles;

    private Set<Product> products;

}
