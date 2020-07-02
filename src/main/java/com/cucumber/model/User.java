package com.cucumber.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "usr")
public class User implements UserDetails {

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

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
    private Set<Product> products;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "buyer", fetch = FetchType.EAGER)
    private Set<Order> ordersForBuyer;

/*    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "seller")
    private Set<Order> ordersForSeller;*/

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
