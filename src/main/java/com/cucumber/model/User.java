package com.cucumber.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
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

    @ManyToMany(mappedBy = "sellers")
    private Set<Product> products;

    @OneToMany(mappedBy = "buyer")
    private Set<Order> ordersForBuyer;

    @OneToMany(mappedBy = "seller")
    private Set<Order> ordersForSeller;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Order> getOrdersForBuyer() {
        return ordersForBuyer;
    }

    public void setOrdersForBuyer(Set<Order> ordersForBuyer) {
        this.ordersForBuyer = ordersForBuyer;
    }

    public Set<Order> getOrdersForSeller() {
        return ordersForSeller;
    }

    public void setOrdersForSeller(Set<Order> ordersForSeller) {
        this.ordersForSeller = ordersForSeller;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        User user = (User) object;

        if (id != user.id) return false;
        if (like != user.like) return false;
        if (active != user.active) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null) return false;
        if (text != null ? !text.equals(user.text) : user.text != null) return false;
        if (activationCode != null ? !activationCode.equals(user.activationCode) : user.activationCode != null)
            return false;
        if (products != null ? !products.equals(user.products) : user.products != null) return false;
        if (ordersForBuyer != null ? !ordersForBuyer.equals(user.ordersForBuyer) : user.ordersForBuyer != null)
            return false;
        if (ordersForSeller != null ? !ordersForSeller.equals(user.ordersForSeller) : user.ordersForSeller != null)
            return false;
        return roles != null ? roles.equals(user.roles) : user.roles == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + like;
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (activationCode != null ? activationCode.hashCode() : 0);
        result = 31 * result + (products != null ? products.hashCode() : 0);
        result = 31 * result + (ordersForBuyer != null ? ordersForBuyer.hashCode() : 0);
        result = 31 * result + (ordersForSeller != null ? ordersForSeller.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }
}
