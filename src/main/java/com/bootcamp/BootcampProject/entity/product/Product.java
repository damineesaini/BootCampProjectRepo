package com.bootcamp.BootcampProject.entity.product;

import com.bootcamp.BootcampProject.entity.user.Seller;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="seller_user_id")
    private Seller sellerUserId;
    private String name;
    @Column(name = "product_description")
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="category_id")
    private Category categoryId;
    @Column(name = "is_cancellable")
    private boolean isCancellable;
    @Column(name = "is_returnable")
    private boolean isReturnable;
    private String brand;
    @Column(name = "is_active")
    private boolean isActive;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Seller getSellerUserId() {
        return sellerUserId;
    }

    public void setSellerUserId(Seller sellerUserId) {
        this.sellerUserId = sellerUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isCancellable() {
        return isCancellable;
    }

    public void setCancellable(boolean cancellable) {
        isCancellable = cancellable;
    }

    public boolean isReturnable() {
        return isReturnable;
    }

    public void setReturnable(boolean returnable) {
        isReturnable = returnable;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

/*
* create table product(
*   id int,
*   seller_user_id int,
*   name varchar(20),
*   description varchar(100),
*   category_id int,
*   is_cancellable boolean,
*   is_returnable boolean,
*   brand varchar(20),
*   is_active boolean,
* foreign key (seller_user_id)
    references seller(user_id),
    * foreign key (category_id)
    references category(id)
* );*/