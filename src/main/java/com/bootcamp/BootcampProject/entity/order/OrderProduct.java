package com.bootcamp.BootcampProject.entity.order;

import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "order_product")
public class OrderProduct {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    private Order orderId;
    private int Quantity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_variation_id")
    private ProductVariation productVariationId;
    @Column(name = "product_variation_metadata")
    private String productVariationMetadata;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public ProductVariation getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(ProductVariation productVariationId) {
        this.productVariationId = productVariationId;
    }

    public String getProductVariationMetadata() {
        return productVariationMetadata;
    }

    public void setProductVariationMetadata(String productVariationMetadata) {
        this.productVariationMetadata = productVariationMetadata;
    }
}


