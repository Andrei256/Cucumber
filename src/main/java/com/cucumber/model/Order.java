package com.cucumber.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "ordr")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "time_of_delivery")
    private LocalDate dateOfDelivery;
    private String address;
    @Column(name = "total_cost")
    private float totalCost;
    private boolean complete;

    @ManyToOne
    @JoinColumn(name = "user_buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "user_seller_id")
    private User seller;

    @OneToMany
    @JoinColumn(name = "product_id")
    private Set<Product> products;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(LocalDate dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
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

        Order order = (Order) object;

        if (id != order.id) return false;
        if (Float.compare(order.totalCost, totalCost) != 0) return false;
        if (complete != order.complete) return false;
        if (dateOfDelivery != null ? !dateOfDelivery.equals(order.dateOfDelivery) : order.dateOfDelivery != null)
            return false;
        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        if (buyer != null ? !buyer.equals(order.buyer) : order.buyer != null) return false;
        if (seller != null ? !seller.equals(order.seller) : order.seller != null) return false;
        return products != null ? products.equals(order.products) : order.products == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (dateOfDelivery != null ? dateOfDelivery.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (totalCost != +0.0f ? Float.floatToIntBits(totalCost) : 0);
        result = 31 * result + (complete ? 1 : 0);
        result = 31 * result + (buyer != null ? buyer.hashCode() : 0);
        result = 31 * result + (seller != null ? seller.hashCode() : 0);
        result = 31 * result + (products != null ? products.hashCode() : 0);
        return result;
    }
}
