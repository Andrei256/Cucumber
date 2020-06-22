package com.cucumber.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "total_cost")
    private float totalCost;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User buyer;

    @OneToMany
    @JoinColumn(name = "product_id")
    private Set<Product> products;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Basket basket = (Basket) object;

        if (id != basket.id) return false;
        if (Float.compare(basket.totalCost, totalCost) != 0) return false;
        if (buyer != null ? !buyer.equals(basket.buyer) : basket.buyer != null) return false;
        return products != null ? products.equals(basket.products) : basket.products == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (totalCost != +0.0f ? Float.floatToIntBits(totalCost) : 0);
        result = 31 * result + (buyer != null ? buyer.hashCode() : 0);
        result = 31 * result + (products != null ? products.hashCode() : 0);
        return result;
    }
}
