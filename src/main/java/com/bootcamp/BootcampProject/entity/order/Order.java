package com.bootcamp.BootcampProject.entity.order;

import com.bootcamp.BootcampProject.entity.user.Customer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "order_details")
public class Order {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_user_id")
    private Customer customerId;
    @Column(name = "amount_paid")
    private double amountPaid;
    @Column(name = "date_created")
    private Date dateCreated;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "customer_city")
    private String customerCity;
    @Column(name = "customer_state")
    private String customerState;
    @Column(name = "customer_country")
    private String customerCountry;
    @Column(name = "customer_address_line")
    private String customerAddressLine;
    @Column(name = "customer_zipcode")
    private int customerZipcode;
    @Column(name = "customer_label")
    private String customerLabel;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getCustomerAddressLine() {
        return customerAddressLine;
    }

    public void setCustomerAddressLine(String customerAddressLine) {
        this.customerAddressLine = customerAddressLine;
    }

    public int getCustomerZipcode() {
        return customerZipcode;
    }

    public void setCustomerZipcode(int customerZipcode) {
        this.customerZipcode = customerZipcode;
    }

    public String getCustomerLabel() {
        return customerLabel;
    }

    public void setCustomerLabel(String customerLabel) {
        this.customerLabel = customerLabel;
    }
}

