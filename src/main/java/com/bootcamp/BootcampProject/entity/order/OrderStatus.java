package com.bootcamp.BootcampProject.entity.order;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

enum Status{ORDER_PLACED,CANCELLED,ORDER_REJECTED,ORDER_CONFIRMED,ORDER_SHIPPED,DELIVERED,RETURN_REQUESTED,
    RETURN_REJECTED,RETURN_APPROVED,PICK_UP_INITIATED,PICK_UP_COMPLETED,REFUND_INITIATED ,REFUND_COMPLETED,CLOSED}

@Entity
@Table(name = "order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @OneToOne(targetEntity = OrderProduct.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProductId;
    @Column(name = "from_status")
    private Status fromStatus;
    @Column(name = "to_status")
    private Status toStatus;
    @Column(name = "transition_notes_comments")
    private String transitionNotesComments;

    public OrderProduct getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(OrderProduct orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Status getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(Status fromStatus) {
        this.fromStatus = fromStatus;
    }

    public Status getToStatus() {
        return toStatus;
    }

    public void setToStatus(Status toStatus) {
        this.toStatus = toStatus;
    }

    public String getTransitionNotesComments() {
        return transitionNotesComments;
    }

    public void setTransitionNotesComments(String transitionNotesComments) {
        this.transitionNotesComments = transitionNotesComments;
    }
}