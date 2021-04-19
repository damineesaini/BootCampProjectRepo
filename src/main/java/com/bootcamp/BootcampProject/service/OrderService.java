package com.bootcamp.BootcampProject.service;

import com.bootcamp.BootcampProject.dto.request.OrderDto;
import com.bootcamp.BootcampProject.entity.order.*;
import com.bootcamp.BootcampProject.entity.product.ProductVariation;
import com.bootcamp.BootcampProject.entity.user.Address;
import com.bootcamp.BootcampProject.entity.user.Customer;
import com.bootcamp.BootcampProject.exception.DoesNotExistException;
import com.bootcamp.BootcampProject.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    ProductVariationRepository productVariationRepository;

    @Autowired
    EmailSendService emailSendService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    public String orderCart(Customer customer, OrderDto orderDto) throws DoesNotExistException {
        List<Cart> cartList = cartRepository.findAllByCustomerId(customer.getId());
        if (cartList.isEmpty()){
            throw new DoesNotExistException("Your cart is empty. Please add items to your cart before order.");
        }
        else {
            Order order = new Order();
            order.setCustomerId(customer);
            order.setDateCreated(new Date());
            Address address = new Address();
            Optional<Address> address1 = addressRepository.findByUserIdAndLabel(customer.getUserId().getId(),orderDto.getLabel());
            if(address1.isPresent()){
                address=address1.get();
                order.setCustomerAddressLine(address.getAddressLine());
                order.setCustomerCity(address.getCity());
                order.setCustomerCountry(address.getCountry());
                order.setCustomerState(address.getState());
                order.setCustomerZipcode(address.getZipcode());
                order.setCustomerLabel(address.getLabel());
                order.setPaymentMethod(orderDto.getPaymentMethod());
            }
        else {
                        throw new DoesNotExistException("The address for the label you mentioned does not exist");
            }

            double amountPaid = 0;

            for (Cart cart:cartList) {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrderId(order);
                orderProduct.setProductVariationId(cart.getProductVariationId());
                orderProduct.setProductVariationMetadata(cart.getProductVariationId().getProductMetadata());
                orderProduct.setQuantity(cart.getQuantity());
                orderProductRepository.save(orderProduct);
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setOrderProductId(orderProduct);
                orderStatus.setFromStatus(Status.ORDER_PLACED);
                orderStatus.setToStatus(Status.ORDER_PLACED);
                orderStatusRepository.save(orderStatus);
                ProductVariation productVariation = productVariationRepository.findById(cart.getProductVariationId().getId()).get();
                int updatedQuantity=productVariation.getQuantityAvailable()-cart.getQuantity();
                productVariation.setQuantityAvailable(updatedQuantity);
                productVariationRepository.save(productVariation);
                amountPaid = amountPaid + cart.getQuantity()*cart.getProductVariationId().getPrice();
            }

            order.setAmountPaid(amountPaid);
            orderRepository.save(order);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(customer.getUserId().getEmail());
            message.setFrom("damineesaini1111@gmail.com");
            message.setSubject("Order Placed Successfully.");
            message.setText("Your order has been placed for the items in your cart.The order id is: "+order.getId()+"\n ThankYou for shopping");
            emailSendService.sendEmail(message);
            logger.info("************************** The order is placed by customer******************************");

            return "order placed successfully. The order id is: "+order.getId();

        }
    }
}
