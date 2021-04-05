package com.bootcamp.BootcampProject.entity.order;

import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "order_product")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @ManyToOne(optional = false)
    @JoinColumn(name="order_id")
    private Order orderId;
    private int Quantity;
    @ManyToOne(optional = false)
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

/*
* create table  order_product(
*   id int,
*   order_id int,
*   quantity int,
*   product_variation_id int,
*   product_variation_metadata varchar(50),
* foreign key (order_id)
    references order(id),
    * foreign key (product_variation_id)
    references product_variation(id)
* );*/
