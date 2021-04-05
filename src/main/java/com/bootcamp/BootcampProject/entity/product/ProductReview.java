package com.bootcamp.BootcampProject.entity.product;

import com.bootcamp.BootcampProject.entity.user.Customer;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "product_review")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name="customer_user_id")
    private Customer customerUserId;
    private String review;
    private int rating;
    @ManyToOne(optional = false)
    @JoinColumn(name="product_id")
    private Product productId;

    public Customer getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(Customer customerUserId) {
        this.customerUserId = customerUserId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }
}

/*
* create table product_review(
*   customer_user_id int,
*   review varchar(100),
*   rating int,
*   product_id int,
* foreign key (customer_user_id)
    references customer(user_id),
    * foreign key (product_id)
    references product(id)
* );
* */