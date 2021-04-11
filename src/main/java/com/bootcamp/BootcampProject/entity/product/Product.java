package com.bootcamp.BootcampProject.entity.product;

import com.bootcamp.BootcampProject.entity.user.Seller;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product")
@JsonFilter("productFilter")
public class Product {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    @JsonProperty
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
    @JsonProperty("isCancellable")
    @Column(name = "is_cancellable")
    private boolean isCancellable;
    @JsonProperty("isReturnable")
    @Column(name = "is_returnable")
    private boolean isReturnable;
    private String brand;
    @Column(name = "is_active")
    private boolean isActive;
    private boolean isDelete;
//    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    private Set<ProductVariation> productVariationId;


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

    public Set<ProductVariation> getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(Set<ProductVariation> productVariationId) {
        this.productVariationId = productVariationId;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }


}

