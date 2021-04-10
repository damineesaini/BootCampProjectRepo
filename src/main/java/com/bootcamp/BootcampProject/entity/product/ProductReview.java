package com.bootcamp.BootcampProject.entity.product;

import com.bootcamp.BootcampProject.entity.user.Customer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "product_review")
public class ProductReview {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_user_id")
    private Customer customerUserId;
    private String review;
    private int rating;
    @ManyToOne(cascade = CascadeType.ALL)
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
