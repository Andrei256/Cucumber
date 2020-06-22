package com.cucumber.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean active;
    private float cost;

    @ManyToMany
    @JoinTable(
            name = "usr_product",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn (name = "users_id" ) }
    )
    private Set<User> sellers;

    @ManyToOne
    @JoinColumn(name = "product_description_id")
    private ProductDescription productDescription;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Set<User> getSellers() {
        return sellers;
    }

    public void setSellers(Set<User> sellers) {
        this.sellers = sellers;
    }

    public ProductDescription getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(ProductDescription productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Product product = (Product) object;

        if (id != product.id) return false;
        if (active != product.active) return false;
        if (Float.compare(product.cost, cost) != 0) return false;
        if (sellers != null ? !sellers.equals(product.sellers) : product.sellers != null) return false;
        return productDescription != null ? productDescription.equals(product.productDescription) : product.productDescription == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (cost != +0.0f ? Float.floatToIntBits(cost) : 0);
        result = 31 * result + (sellers != null ? sellers.hashCode() : 0);
        result = 31 * result + (productDescription != null ? productDescription.hashCode() : 0);
        return result;
    }
}
