package com.bootcamp.BootcampProject.entity.order;

import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import com.bootcamp.BootcampProject.entity.user.Customer;
import com.fasterxml.jackson.annotation.JsonFilter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "cart")
@JsonFilter("cartFilter")
public class Cart {
    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_user_id")
    private Customer customerUserId;
    private int quantity;
    @Column(name = "is_wishlist_item")
    private boolean isWishListItem;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariationId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Customer getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(Customer customerUserId) {
        this.customerUserId = customerUserId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isWishListItem() {
        return isWishListItem;
    }

    public void setWishListItem(boolean wishListItem) {
        isWishListItem = wishListItem;
    }

    public ProductVariation getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(ProductVariation productVariationId) {
        this.productVariationId = productVariationId;
    }
}

