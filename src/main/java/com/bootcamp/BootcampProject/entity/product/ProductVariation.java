package com.bootcamp.BootcampProject.entity.product;

import com.bootcamp.BootcampProject.entity.image.Image;
import com.bootcamp.BootcampProject.utility.HashMapConverter;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "product_variation" )
@JsonFilter("productVariationFilter")
public class ProductVariation {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    @JsonProperty
    private UUID id;
//    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    private Product productId;
    @Column(name = "quantity_available")
    private int quantityAvailable;
    private double price;
    @Convert(converter = HashMapConverter.class)
    @Column(name = "product_metadata",columnDefinition = "JSON")
    private Map<String,String> productMetadata;
    @Column(name = "is_active")
    private boolean isActive;
    private boolean isDeleted;
    @OneToOne(cascade = CascadeType.ALL)
    private Image productImage;

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

    public Map<String,String> getProductMetadata() {
        return productMetadata;
    }

    public void setProductMetadata(Map<String,String> productMetadata) {
        this.productMetadata = productMetadata;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Image getProductImage() {
        return productImage;
    }

    public void setProductImage(Image productImage) {
        this.productImage = productImage;
    }
}
