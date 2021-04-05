package com.bootcamp.BootcampProject.entity.order;

import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @OneToOne(targetEntity = Customer.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_user_id")
    private Customer customerUserId;
    private int quantity;
    @Column(name = "is_wishlist_item")
    private boolean isWishListItem;
    @OneToOne(targetEntity = ProductVariation.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariationId;

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

/*
* create table cart(
*   customer_user_id int,
*   quantity int,
*   is_wishlist_item boolean,
*   product_variation_id int,
*   foreign key (customer_user_id)
*   references customer(user_id)
*   foreign key (product_variation_id)
*   references product_variation(id)
* );*/