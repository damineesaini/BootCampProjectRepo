package com.bootcamp.BootcampProject.entity.product;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "product_variation" )
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name="product_id")
    private Product productId;
    @Column(name = "quantity_available")
    private int quantityAvailable;
    private double price;
    @Column(name = "product_metadata")
    private String productMetadata;
    @Column(name = "is_active")
    private boolean isActive;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductMetadata() {
        return productMetadata;
    }

    public void setProductMetadata(String productMetadata) {
        this.productMetadata = productMetadata;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
/*
* create table product_variation(
*   id int,
*   product_id int,
*   quantity_available int,
*   price double,
*   product_metadata varchar(100),
*   is_active boolean,
*   foreign key (product_id)
    references product(id),
* );
* */